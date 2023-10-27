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

package dev.kurykat.morematerials.registries;

import com.google.common.base.Suppliers;
import dev.kurykat.morematerials.MoreMaterialsConstants;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class MoreMaterialsConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(
            Registry.CONFIGURED_FEATURE_REGISTRY,
            MoreMaterialsConstants.MOD_ID
    );

    private static final Supplier<List<OreConfiguration.TargetBlockState>> RUBY_OVERWORLD_REPLACEMENT = Suppliers.memoize(
            () -> List.of(
                    OreConfiguration.target(
                            OreFeatures.STONE_ORE_REPLACEABLES,
                            MoreMaterialsBlocks.RUBY_ORE.get().defaultBlockState()
                    ),
                    OreConfiguration.target(
                            OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                            MoreMaterialsBlocks.DEEPSLATE_RUBY_ORE.get().defaultBlockState()
                    )
            )
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_RUBY_OVERWORLD = CONFIGURED_FEATURES.register(
            "ore_ruby_overworld",
            () -> new ConfiguredFeature<>(
                    Feature.ORE,
                    new OreConfiguration(RUBY_OVERWORLD_REPLACEMENT.get(), 5)
            )
    );
}
