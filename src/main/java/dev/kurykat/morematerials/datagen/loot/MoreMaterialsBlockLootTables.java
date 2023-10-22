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

package dev.kurykat.morematerials.datagen.loot;

import dev.kurykat.morematerials.init.MoreMaterialsBlockInit;
import dev.kurykat.morematerials.init.MoreMaterialsItemInit;
import dev.kurykat.morematerials.utils.RegistryUtils;
import dev.kurykat.morematerials.utils.ResourcesUtils;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MoreMaterialsBlockLootTables extends BlockLoot {
    @Override
    protected void addTables() {
        MoreMaterialsBlockInit.SIMPLE_BLOCKS.forEach(
                blockRegistryObject -> dropSelf(blockRegistryObject.get())
        );

        MoreMaterialsBlockInit.ORE_BLOCKS.forEach(
                blockRegistryObject -> add(
                        blockRegistryObject.get(),
                        (block) -> createOreDrop(
                                block,
                                Objects.requireNonNull(RegistryUtils.getRegistryObjectByName(
                                        MoreMaterialsItemInit.RESOURCE_ITEMS,
                                        ResourcesUtils.getResourceNameFromBlockName(blockRegistryObject.getId().getPath())
                                )).get()
                        )
                )
        );
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return MoreMaterialsBlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
