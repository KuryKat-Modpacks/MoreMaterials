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
import dev.kurykat.morematerials.init.MoreMaterialsBlockInit;
import dev.kurykat.morematerials.init.MoreMaterialsItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class MoreMaterialsLanguageProvider extends LanguageProvider {
    public MoreMaterialsLanguageProvider(DataGenerator generator) {
        super(generator, Constants.MOD_ID, "en_us");
    }

    @Override
    public @NotNull String getName() {
        return Constants.MOD_NAME + super.getName();
    }

    @Override
    protected void addTranslations() {
        addBlocks();
        addItems();
    }

    private void addBlocks() {
        MoreMaterialsBlockInit.SIMPLE_BLOCKS.forEach(
                blockRegistryObject -> addBlock(
                        blockRegistryObject,
                        parseNameFromPath(blockRegistryObject.getId().getPath())
                )
        );
        MoreMaterialsBlockInit.ORE_BLOCKS.forEach(
                blockRegistryObject -> addBlock(
                        blockRegistryObject,
                        parseNameFromPath(blockRegistryObject.getId().getPath())
                )
        );
    }

    private void addItems() {
        MoreMaterialsItemInit.RESOURCE_ITEMS.forEach(
                itemRegistryObject -> addItem(
                        itemRegistryObject,
                        parseNameFromPath(itemRegistryObject.getId().getPath())
                )
        );
        MoreMaterialsItemInit.SIMPLE_ITEMS.forEach(
                itemRegistryObject -> addItem(
                        itemRegistryObject,
                        parseNameFromPath(itemRegistryObject.getId().getPath())
                )
        );
        MoreMaterialsItemInit.HANDHELD_ITEMS.forEach(
                itemRegistryObject -> addItem(
                        itemRegistryObject,
                        parseNameFromPath(itemRegistryObject.getId().getPath())
                )
        );
    }

    private String parseNameFromPath(String blockName) {
        return Arrays.stream(blockName.split("_"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .reduce((s1, s2) -> s1 + " " + s2)
                .orElse("");
    }
}
