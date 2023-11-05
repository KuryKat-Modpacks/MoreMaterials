/*
 *     MoreMaterials - Minecraft Mod
 *     Copyright (C) 2023 KuryKat
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.kurykat.morematerials.infrastructure.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.kurykat.morematerials.foundation.config.BaseConfig;
import dev.kurykat.morematerials.foundation.util.Couple;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OreFeatureConfigEntry extends BaseConfig {
    public static final Map<ResourceLocation, OreFeatureConfigEntry> ALL = new HashMap<>();

    public static final Codec<OreFeatureConfigEntry> CODEC = ResourceLocation.CODEC
            .comapFlatMap(OreFeatureConfigEntry::read, entry -> entry.id);

    public final ResourceLocation id;
    public final IntConfig clusterSize;
    public final FloatConfig frequency;
    public final IntConfig minHeight;
    public final IntConfig maxHeight;

    private DataGenExtension dataGenExtension;


    public OreFeatureConfigEntry(ResourceLocation id, int clusterSize, float frequency, int minHeight, int maxHeight) {
        this.id = id;
        this.clusterSize = intConfig(clusterSize, 0, Integer.MAX_VALUE, "clusterSize");
        this.frequency = floatConfig(clusterSize, 0, 512, "frequency",
                "Amount of clusters generated per Chunk.",
                "  >1 to spawn multiple.",
                "  <1 to make it a chance.",
                "  0 to disable."
        );
        this.minHeight = intConfig(minHeight, "minHeight");
        this.maxHeight = intConfig(maxHeight, "maxHeight");

        ALL.put(id, this);
    }


    @Nullable
    public StandardDataGenExtension standardDataGenExtension() {
        if (dataGenExtension == null) {
            dataGenExtension = new StandardDataGenExtension();
        }

        if (dataGenExtension instanceof StandardDataGenExtension standard) {
            return standard;
        }

        return null;
    }

    @Nullable
    public LayeredDataGenExtension layeredDataGenExtension() {
        if (dataGenExtension == null) {
            dataGenExtension = new LayeredDataGenExtension();
        }

        if (dataGenExtension instanceof LayeredDataGenExtension layered) {
            return layered;
        }

        return null;
    }

    @Nullable
    public DataGenExtension dataGenExtension() {
        if (dataGenExtension != null) {
            return dataGenExtension;
        }

        return null;
    }

    public void addToConfig(ForgeConfigSpec.Builder builder) {
        registerAll(builder);
    }

    @Override
    public String getName() {
        return id.getPath();
    }

    public static DataResult<OreFeatureConfigEntry> read(ResourceLocation id) {
        OreFeatureConfigEntry entry = ALL.get(id);
        if (entry != null) {
            return DataResult.success(entry);
        } else {
            return DataResult.error("Invalid OreFeatureConfigEntry: " + id);
        }
    }

    public abstract class DataGenExtension {
        public TagKey<Biome> biomeTag;

        public DataGenExtension biomeTag(TagKey<Biome> biomes) {
            this.biomeTag = biomes;
            return this;
        }

        public abstract ConfiguredFeature<?, ?> createConfiguredFeature(RegistryAccess registryAccess);

        public PlacedFeature createPlacedFeature(RegistryAccess registryAccess) {
            Registry<ConfiguredFeature<?, ?>> configuredFeatureRegistry = registryAccess.registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY);
            Holder<ConfiguredFeature<?, ?>> configuredFeatureHolder = configuredFeatureRegistry.getOrCreateHolderOrThrow(ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, id));
            return new PlacedFeature(configuredFeatureHolder, List.of(new ConfigDrivenPlacement(OreFeatureConfigEntry.this)));
        }

        public BiomeModifier createBiomeModifier(RegistryAccess registryAccess) {
            Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registry.BIOME_REGISTRY);
            Registry<PlacedFeature> placedFeatureRegistry = registryAccess.registryOrThrow(Registry.PLACED_FEATURE_REGISTRY);
            HolderSet<Biome> biomes = new HolderSet.Named<>(biomeRegistry, biomeTag);
            Holder<PlacedFeature> placedFeatureHolder = placedFeatureRegistry.getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, id));
            return new AddFeaturesBiomeModifier(biomes, HolderSet.direct(placedFeatureHolder), GenerationStep.Decoration.UNDERGROUND_ORES);
        }

        public OreFeatureConfigEntry parent() {
            return OreFeatureConfigEntry.this;
        }
    }

    public class StandardDataGenExtension extends DataGenExtension {
        public NonNullSupplier<? extends Block> block;
        public NonNullSupplier<? extends Block> deepBlock;
        public NonNullSupplier<? extends Block> netherBlock;
        public NonNullSupplier<? extends Block> endBlock;

        public StandardDataGenExtension withBlock(NonNullSupplier<? extends Block> block) {
            this.block = block;
            this.deepBlock = block;
            return this;
        }

        public StandardDataGenExtension withBlocks(Couple<NonNullSupplier<? extends Block>> blocks) {
            this.block = blocks.getFirst();
            this.deepBlock = blocks.getSecond();
            return this;
        }

        public StandardDataGenExtension withNetherBlock(NonNullSupplier<? extends Block> block) {
            this.netherBlock = block;
            return this;
        }

        public StandardDataGenExtension withEndBlock(NonNullSupplier<? extends Block> block) {
            this.endBlock = block;
            return this;
        }

        @Override
        public DataGenExtension biomeTag(TagKey<Biome> biomes) {
            super.biomeTag(biomes);
            return this;
        }

        @Override
        public ConfiguredFeature<?, ?> createConfiguredFeature(RegistryAccess registryAccess) {
            List<TargetBlockState> targetBlockStates = new ArrayList<>();
            if (block != null) {
                targetBlockStates.add(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, block.get().defaultBlockState()));
            }
            if (deepBlock != null) {
                targetBlockStates.add(OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepBlock.get().defaultBlockState()));
            }
            if (netherBlock != null) {
                targetBlockStates.add(OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, netherBlock.get().defaultBlockState()));
            }
            if (endBlock != null) {
                targetBlockStates.add(OreConfiguration.target(new BlockMatchTest(Blocks.END_STONE), endBlock.get().defaultBlockState()));
            }
            ConfigDrivenOreFeatureConfiguration config = new ConfigDrivenOreFeatureConfiguration(OreFeatureConfigEntry.this, 0, targetBlockStates);
            return new ConfiguredFeature<>(MoreMaterialsFeatures.STANDARD_ORE.get(), config);
        }
    }

    public class LayeredDataGenExtension extends DataGenExtension {
        public final List<NonNullSupplier<LayerPattern>> layerPatterns = new ArrayList<>();

        public LayeredDataGenExtension withLayerPattern(NonNullSupplier<LayerPattern> pattern) {
            this.layerPatterns.add(pattern);
            return this;
        }

        @Override
        public DataGenExtension biomeTag(TagKey<Biome> biomes) {
            super.biomeTag(biomes);
            return this;
        }

        @Override
        public ConfiguredFeature<?, ?> createConfiguredFeature(RegistryAccess registryAccess) {
            List<LayerPattern> layerPatterns = this.layerPatterns
                    .stream()
                    .map(NonNullSupplier::get)
                    .toList();

            ConfigDrivenLayeredOreFeatureConfiguration config = new ConfigDrivenLayeredOreFeatureConfiguration(OreFeatureConfigEntry.this, 0, layerPatterns);
            return new ConfiguredFeature<>(MoreMaterialsFeatures.LAYERED_ORE.get(), config);
        }
    }
}
