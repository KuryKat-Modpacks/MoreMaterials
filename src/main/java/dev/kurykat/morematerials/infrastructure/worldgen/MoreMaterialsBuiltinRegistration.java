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
import dev.kurykat.morematerials.infrastructure.worldgen.OreFeatureConfigEntry.DataGenExtension;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class MoreMaterialsBuiltinRegistration {
    private static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE_REGISTER = DeferredRegister
            .create(Registry.CONFIGURED_FEATURE_REGISTRY, MoreMaterials.MOD_ID);
    private static final DeferredRegister<PlacedFeature> PLACED_FEATURE_REGISTER = DeferredRegister
            .create(Registry.PLACED_FEATURE_REGISTRY, MoreMaterials.MOD_ID);
    private static final DeferredRegister<BiomeModifier> BIOME_MODIFIER_REGISTER = DeferredRegister
            .create(ForgeRegistries.Keys.BIOME_MODIFIERS, MoreMaterials.MOD_ID);

    static {
        for (Map.Entry<ResourceLocation, OreFeatureConfigEntry> entry : OreFeatureConfigEntry.ALL.entrySet()) {
            ResourceLocation id = entry.getKey();
            if (id.getNamespace().equals(MoreMaterials.MOD_ID)) {
                DataGenExtension dataGenExtension = entry.getValue().dataGenExtension();
                if (dataGenExtension != null) {
                    CONFIGURED_FEATURE_REGISTER
                            .register(id.getPath(), () -> dataGenExtension.createConfiguredFeature(BuiltinRegistries.ACCESS));
                    PLACED_FEATURE_REGISTER
                            .register(id.getPath(), () -> dataGenExtension.createPlacedFeature(BuiltinRegistries.ACCESS));
                    BIOME_MODIFIER_REGISTER
                            .register(id.getPath(), () -> dataGenExtension.createBiomeModifier(BuiltinRegistries.ACCESS));
                }
            }
        }
    }

    public static void register(IEventBus bus) {
        CONFIGURED_FEATURE_REGISTER.register(bus);
        PLACED_FEATURE_REGISTER.register(bus);
        BIOME_MODIFIER_REGISTER.register(bus);
    }
}
