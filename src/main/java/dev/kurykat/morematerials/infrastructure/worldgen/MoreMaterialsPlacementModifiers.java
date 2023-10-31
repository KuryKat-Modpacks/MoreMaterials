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

import dev.kurykat.morematerials.MoreMaterialsConstants;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MoreMaterialsPlacementModifiers {
    private static final DeferredRegister<PlacementModifierType<?>> REGISTER = DeferredRegister.create(Registry.PLACEMENT_MODIFIER_REGISTRY, MoreMaterialsConstants.MOD_ID);

    public static final RegistryObject<PlacementModifierType<ConfigDrivenPlacement>> CONFIG_DRIVEN = REGISTER.register("config_driven", () -> () -> ConfigDrivenPlacement.CODEC);

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
    }
}
