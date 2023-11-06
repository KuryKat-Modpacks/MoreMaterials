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

import dev.kurykat.morematerials.MoreMaterials;
import dev.kurykat.morematerials.foundation.data.DynamicDataProvider;
import dev.kurykat.morematerials.foundation.util.Couple;
import dev.kurykat.morematerials.infrastructure.worldgen.OreFeatureConfigEntry.DataGenExtension;
import dev.kurykat.morematerials.registries.MoreMaterialsBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class MoreMaterialsOreFeatureConfigEntries {
    public static final OreFeatureConfigEntry RUBY_ORE = create("ruby_ore", 8, 8, -63, 30)
            .standardDataGenExtension()
            .withBlocks(Couple.create(MoreMaterialsBlocks.RUBY_ORE, MoreMaterialsBlocks.DEEPSLATE_RUBY_ORE))
            .biomeTag(BiomeTags.IS_OVERWORLD)
            .parent();

    public static final OreFeatureConfigEntry ALEXANDRITE_ORE = create("alexandrite_ore", 8, 8, -63, 128)
            .standardDataGenExtension()
            .withBlocks(Couple.create(MoreMaterialsBlocks.ALEXANDRITE_ORE, MoreMaterialsBlocks.DEEPSLATE_ALEXANDRITE_ORE))
            .biomeTag(BiomeTags.IS_OVERWORLD)
            .parent();

    public static final OreFeatureConfigEntry CELESLAR_ORE = create("celeslar_ore", 8, 8, -63, 125)
            .standardDataGenExtension()
            .withEndBlock(MoreMaterialsBlocks.END_CELESLAR_ORE)
            .biomeTag(BiomeTags.IS_END)
            .parent();

    private static OreFeatureConfigEntry create(String name, int clusterSize, float frequency, int minHeight, int maxHeight) {
        ResourceLocation id = MoreMaterials.asResource(name);
        return new OreFeatureConfigEntry(id, clusterSize, frequency, minHeight, maxHeight);
    }

    public static void fillConfig(ForgeConfigSpec.Builder builder, String namespace) {
        OreFeatureConfigEntry.ALL
                .forEach((id, entry) -> {
                    if (id.getNamespace().equals(namespace)) {
                        builder.push(entry.getName());
                        entry.addToConfig(builder);
                        builder.pop();
                    }
                });
    }

    public static void init() {
    }

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        RegistryAccess registryAccess = RegistryAccess.BUILTIN.get();

        //

        Map<ResourceLocation, ConfiguredFeature<?, ?>> configuredFeatures = new HashMap<>();
        for (Map.Entry<ResourceLocation, OreFeatureConfigEntry> entry : OreFeatureConfigEntry.ALL.entrySet()) {
            DataGenExtension dataGenExt = entry.getValue().dataGenExtension();
            if (dataGenExt != null) {
                configuredFeatures.put(entry.getKey(), dataGenExt.createConfiguredFeature(registryAccess));
            }
        }

        DynamicDataProvider<ConfiguredFeature<?, ?>> configuredFeatureProvider = DynamicDataProvider.create(generator, "Create's Configured Features", registryAccess, Registry.CONFIGURED_FEATURE_REGISTRY, configuredFeatures);
        if (configuredFeatureProvider != null) {
            generator.addProvider(true, configuredFeatureProvider);
        }

        //

        Map<ResourceLocation, PlacedFeature> placedFeatures = new HashMap<>();
        for (Map.Entry<ResourceLocation, OreFeatureConfigEntry> entry : OreFeatureConfigEntry.ALL.entrySet()) {
            DataGenExtension dataGenExt = entry.getValue().dataGenExtension();
            if (dataGenExt != null) {
                placedFeatures.put(entry.getKey(), dataGenExt.createPlacedFeature(registryAccess));
            }
        }

        DynamicDataProvider<PlacedFeature> placedFeatureProvider = DynamicDataProvider.create(generator, "Create's Placed Features", registryAccess, Registry.PLACED_FEATURE_REGISTRY, placedFeatures);
        if (placedFeatureProvider != null) {
            generator.addProvider(true, placedFeatureProvider);
        }

        //

        Map<ResourceLocation, BiomeModifier> biomeModifiers = new HashMap<>();
        for (Map.Entry<ResourceLocation, OreFeatureConfigEntry> entry : OreFeatureConfigEntry.ALL.entrySet()) {
            DataGenExtension dataGenExt = entry.getValue().dataGenExtension();
            if (dataGenExt != null) {
                biomeModifiers.put(entry.getKey(), dataGenExt.createBiomeModifier(registryAccess));
            }
        }

        DynamicDataProvider<BiomeModifier> biomeModifierProvider = DynamicDataProvider.create(generator, "Create's Biome Modifiers", registryAccess, ForgeRegistries.Keys.BIOME_MODIFIERS, biomeModifiers);
        if (biomeModifierProvider != null) {
            generator.addProvider(true, biomeModifierProvider);
        }
    }
}
