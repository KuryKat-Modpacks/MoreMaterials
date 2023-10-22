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

package dev.kurykat.morematerials.datagen;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.kurykat.morematerials.Constants;
import dev.kurykat.morematerials.init.MoreMaterialsPlacedFeatureInit;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MoreMaterialsBiomeModifierProvider implements DataProvider {
    private final DataGenerator generator;
    private final ExistingFileHelper existingFileHelper;
    private final Map<ResourceLocation, BiomeModifier> toSerialize = new HashMap<>();

    public MoreMaterialsBiomeModifierProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        this.generator = generator;
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    public @NotNull String getName() {
        return Constants.MOD_NAME + "BiomeModifiers";
    }

    protected void addModifiers(RegistryOps<JsonElement> registryOps) {
        final var biomeRegistry = registryOps.registry(Registry.BIOME_REGISTRY).orElseThrow();
        final var featureRegistry = registryOps.registry(Registry.PLACED_FEATURE_REGISTRY).orElseThrow();

        addModifier(
                "ore_ruby_overworld",
                new AddFeaturesBiomeModifier(
                        tag(biomeRegistry, BiomeTags.IS_OVERWORLD),
                        feature(featureRegistry, MoreMaterialsPlacedFeatureInit.ORE_RUBY_OVERWORLD),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );

    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException {
        RegistryOps<JsonElement> registryOps = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());

        addModifiers(registryOps);

        JsonCodecProvider<BiomeModifier> provider = JsonCodecProvider.forDatapackRegistry(
                generator,
                existingFileHelper,
                Constants.MOD_ID,
                registryOps,
                ForgeRegistries.Keys.BIOME_MODIFIERS,
                toSerialize
        );

        provider.run(cache);
    }

    protected <T extends BiomeModifier> void addModifier(String name, T biomeModifier) {
        toSerialize.put(new ResourceLocation(Constants.MOD_ID, name), biomeModifier);
    }

    protected HolderSet<Biome> tag(Registry<Biome> registry, TagKey<Biome> key) {
        return new HolderSet.Named<>(registry, key);
    }

    protected HolderSet<PlacedFeature> feature(Registry<PlacedFeature> registry, RegistryObject<PlacedFeature> feature) {
        ResourceKey<PlacedFeature> key = Preconditions.checkNotNull(feature.getKey(), "%s has no registry key.", feature.get());
        return HolderSet.direct(registry.getHolderOrThrow(key));
    }
}
