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
import dev.kurykat.morematerials.init.MoreMaterialsItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class MoreMaterialsItemModelProvider extends ItemModelProvider {
    public MoreMaterialsItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public @NotNull String getName() {
        return Constants.MOD_NAME + "ItemModels";
    }

    @Override
    protected void registerModels() {
        MoreMaterialsItemInit.RESOURCE_ITEMS.forEach(this::simpleItem);
        MoreMaterialsItemInit.SIMPLE_ITEMS.forEach(this::simpleItem);
        MoreMaterialsItemInit.HANDHELD_ITEMS.forEach(this::handheldItem);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(
                item.getId().getPath(),
                new ResourceLocation("item/generated")
        ).texture(
                "layer0",
                new ResourceLocation(Constants.MOD_ID, "item/" + item.getId().getPath())
        );
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(
                item.getId().getPath(),
                new ResourceLocation("item/handheld")
        ).texture(
                "layer0",
                new ResourceLocation(Constants.MOD_ID, "item/" + item.getId().getPath())
        );
    }
}
