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

package dev.kurykat.morematerials.init;

import dev.kurykat.morematerials.Constants;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class MoreMaterialsPlacedFeatureInit {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(
            Registry.PLACED_FEATURE_REGISTRY,
            Constants.MOD_ID
    );

    public static final RegistryObject<PlacedFeature> ORE_RUBY_OVERWORLD = PLACED_FEATURES.register(
            "ore_ruby_overworld",
            () -> new PlacedFeature(
                    MoreMaterialsConfiguredFeatureInit.ORE_RUBY_OVERWORLD.getHolder().get(),
                    commonOrePlacement(
                            4,
                            HeightRangePlacement.triangle(
                                    VerticalAnchor.BOTTOM,
                                    VerticalAnchor.aboveBottom(100)
                            )
                    )
            )
    );

    public static List<PlacementModifier> commonOrePlacement(int countPerChunk, PlacementModifier height) {
        return orePlacement(CountPlacement.of(countPerChunk), height);
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier count, PlacementModifier height) {
        return List.of(count, InSquarePlacement.spread(), height, BiomeFilter.biome());
    }
}
