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
import dev.kurykat.morematerials.init.MoreMaterialsTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MoreMaterialsItemTagsProvider extends ItemTagsProvider {
    public MoreMaterialsItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public @NotNull String getName() {
        return Constants.MOD_NAME + super.getName();
    }

    @Override
    protected void addTags() {
        MoreMaterialsTags.Items.ORES.forEach(itemTagKey ->
                MoreMaterialsTags.Blocks.ORES.stream()
                        .filter(blockTagKey -> blockTagKey.location().getPath().equals(itemTagKey.location().getPath()))
                        .findFirst()
                        .ifPresent(blockTagKey -> copy(blockTagKey, itemTagKey))
        );

        MoreMaterialsTags.Items.RESOURCE_STORAGE_BLOCKS.forEach(itemTagKey ->
                MoreMaterialsTags.Blocks.RESOURCE_STORAGE_BLOCKS.stream()
                        .filter(blockTagKey -> blockTagKey.location().getPath().equals(itemTagKey.location().getPath()))
                        .findFirst()
                        .ifPresent(blockTagKey -> copy(blockTagKey, itemTagKey))
        );
    }
}
