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

import dev.kurykat.morematerials.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoreMaterialsDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // CLIENT
        generator.addProvider(event.includeClient(), new MoreMaterialsLanguageProvider(generator));
        MoreMaterialsItemModelProvider itemModelProvider = new MoreMaterialsItemModelProvider(generator, existingFileHelper);
        generator.addProvider(event.includeClient(), itemModelProvider);
        generator.addProvider(event.includeClient(), new MoreMaterialsBlockStateProvider(generator, itemModelProvider.existingFileHelper));

        // SERVER
        generator.addProvider(event.includeServer(), new MoreMaterialsRecipeProvider(generator));
        generator.addProvider(event.includeServer(), new MoreMaterialsLootTableProvider(generator));
        generator.addProvider(event.includeServer(), new MoreMaterialsBiomeModifierProvider(generator, existingFileHelper));
        MoreMaterialsBlockTagsProvider blockTagsProvider = new MoreMaterialsBlockTagsProvider(generator, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new MoreMaterialsItemTagsProvider(generator, blockTagsProvider, existingFileHelper));
    }

}
