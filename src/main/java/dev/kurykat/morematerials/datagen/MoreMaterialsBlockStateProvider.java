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
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

/**
 * The BlockState Provider will deal with Generating all JSON files related to blocks, such as:
 * <ul>
 *   <li>BlockState</li>
 *   <li>Block Model</li>
 *   <li>Block Item Model</li>
 * </ul>
 */
public class MoreMaterialsBlockStateProvider extends BlockStateProvider {
    public MoreMaterialsBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Constants.MOD_ID, exFileHelper);
    }

    @Override
    public @NotNull String getName() {
        return Constants.MOD_NAME + "BlockStates";
    }

    @Override
    protected void registerStatesAndModels() {
        MoreMaterialsBlockInit.SIMPLE_BLOCKS.forEach(this::simpleBlockWithItem);
        MoreMaterialsBlockInit.ORE_BLOCKS.forEach(this::simpleBlockWithItem);
    }

    private void simpleBlockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get());
        simpleBlockItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
