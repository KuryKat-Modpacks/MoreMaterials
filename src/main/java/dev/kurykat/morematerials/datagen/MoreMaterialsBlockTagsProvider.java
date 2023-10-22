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
import dev.kurykat.morematerials.init.MoreMaterialsTags;
import dev.kurykat.morematerials.utils.RegistryUtils;
import dev.kurykat.morematerials.utils.ResourcesUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class MoreMaterialsBlockTagsProvider extends BlockTagsProvider {
    public MoreMaterialsBlockTagsProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public @NotNull String getName() {
        return Constants.MOD_NAME + super.getName();
    }

    @Override
    protected void addTags() {
        MoreMaterialsBlockInit.ORE_BLOCKS.forEach(blockRegistryObject ->
                tag(BlockTags.MINEABLE_WITH_PICKAXE).add(blockRegistryObject.get())
        );

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                MoreMaterialsBlockInit.RUBY_BLOCK.get()
        );

        MoreMaterialsTags.Blocks.ORES.forEach(blockTagKey -> {
            String resourceName = ResourcesUtils.getResourceNameFromBlockTag(blockTagKey.location().getPath());
            ArrayList<String> possibleBlockNames = ResourcesUtils.getBlockNameFromResourceName(resourceName);

            possibleBlockNames.forEach(possibleBlockName -> {
                Block block = RegistryUtils.getRegistryObjectByName(
                        MoreMaterialsBlockInit.ORE_BLOCKS,
                        possibleBlockName
                ).get();

                if (block != null) {
                    tag(blockTagKey).add(block);
                }
            });
        });

        MoreMaterialsTags.Blocks.RESOURCE_STORAGE_BLOCKS.forEach(blockTagKey -> {
            String resourceName = ResourcesUtils.getResourceNameFromBlockTag(blockTagKey.location().getPath());
            ArrayList<String> possibleBlockNames = ResourcesUtils.getBlockNameFromResourceName(resourceName);

            possibleBlockNames.forEach(possibleBlockName -> {
                Block block = RegistryUtils.getRegistryObjectByName(
                        MoreMaterialsBlockInit.SIMPLE_BLOCKS,
                        possibleBlockName
                ).get();

                if (block != null) {
                    tag(blockTagKey).add(block);
                }
            });
        });
    }
}
