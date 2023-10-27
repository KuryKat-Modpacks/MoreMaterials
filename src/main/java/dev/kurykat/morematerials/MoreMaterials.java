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

package dev.kurykat.morematerials;

import dev.kurykat.morematerials.registries.MoreMaterialsBlocks;
import dev.kurykat.morematerials.registries.MoreMaterialsConfiguredFeatures;
import dev.kurykat.morematerials.registries.MoreMaterialsItems;
import dev.kurykat.morematerials.registries.MoreMaterialsPlacedFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MoreMaterialsConstants.MOD_ID)
public class MoreMaterials {
    public MoreMaterials() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MoreMaterialsItems.ITEMS.register(modEventBus);
        MoreMaterialsBlocks.BLOCKS.register(modEventBus);
        MoreMaterialsConfiguredFeatures.CONFIGURED_FEATURES.register(modEventBus);
        MoreMaterialsPlacedFeatures.PLACED_FEATURES.register(modEventBus);
    }

    public static ResourceLocation resourceLocation(String path) {
        return new ResourceLocation(MoreMaterialsConstants.MOD_ID, path);
    }
}
