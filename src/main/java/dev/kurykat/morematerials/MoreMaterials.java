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

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.kurykat.morematerials.infrastructure.config.MoreMaterialsConfigs;
import dev.kurykat.morematerials.infrastructure.worldgen.MoreMaterialsBuiltinRegistration;
import dev.kurykat.morematerials.infrastructure.worldgen.MoreMaterialsFeatures;
import dev.kurykat.morematerials.infrastructure.worldgen.MoreMaterialsOreFeatureConfigEntries;
import dev.kurykat.morematerials.infrastructure.worldgen.MoreMaterialsPlacementModifiers;
import dev.kurykat.morematerials.registries.MoreMaterialsBlocks;
import dev.kurykat.morematerials.registries.MoreMaterialsItems;
import dev.kurykat.morematerials.tags.MoreMaterialsTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("unused")
@Mod(MoreMaterialsConstants.MOD_ID)
public class MoreMaterials {
    private static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MoreMaterialsConstants.MOD_ID));

    public static Registrate getRegistrate() {
        return REGISTRATE.get();
    }

    public MoreMaterials() {
        MoreMaterialsConstants.LOGGER.info("{} is Starting! Hello World!", MoreMaterialsConstants.MOD_NAME);
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        MoreMaterialsTags.init();
        MoreMaterialsItems.register();
        MoreMaterialsBlocks.register();

        MoreMaterialsOreFeatureConfigEntries.init();
        MoreMaterialsFeatures.register(modEventBus);
        MoreMaterialsPlacementModifiers.register(modEventBus);
        MoreMaterialsBuiltinRegistration.register(modEventBus);

        MoreMaterialsConfigs.register(modLoadingContext);
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MoreMaterialsConstants.MOD_ID, path);
    }
}
