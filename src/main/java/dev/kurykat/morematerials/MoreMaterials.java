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

import dev.kurykat.morematerials.init.MoreMaterialsBlockInit;
import dev.kurykat.morematerials.init.MoreMaterialsConfiguredFeatureInit;
import dev.kurykat.morematerials.init.MoreMaterialsItemInit;
import dev.kurykat.morematerials.init.MoreMaterialsPlacedFeatureInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class MoreMaterials {
    public MoreMaterials() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MoreMaterialsItemInit.ITEMS.register(modEventBus);
        MoreMaterialsBlockInit.BLOCKS.register(modEventBus);
        MoreMaterialsConfiguredFeatureInit.CONFIGURED_FEATURES.register(modEventBus);
        MoreMaterialsPlacedFeatureInit.PLACED_FEATURES.register(modEventBus);
    }
}
