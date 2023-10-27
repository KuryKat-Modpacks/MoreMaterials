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

package dev.kurykat.morematerials.registries;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.kurykat.morematerials.MoreMaterials;
import dev.kurykat.morematerials.MoreMaterialsConstants;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.Tags;

import static dev.kurykat.morematerials.data.TagGen.pickaxeOnly;
import static dev.kurykat.morematerials.data.TagGen.tagBlockAndItem;

@SuppressWarnings("unused")
public class MoreMaterialsBlocks {
    private static final Registrate REGISTRATE = MoreMaterials.getRegistrate();

    static {
        REGISTRATE.creativeModeTab(() -> MoreMaterialsConstants.CREATIVE_TAB);
    }

    public static final BlockEntry<DropExperienceBlock> RUBY_ORE = REGISTRATE
            .block("ruby_ore", properties -> new DropExperienceBlock(properties, UniformInt.of(3, 7)))
            .initialProperties(Material.STONE)
            .properties(properties -> properties
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 3.0F)
                    .sound(SoundType.STONE)
            )
            .transform(pickaxeOnly())
            .loot((lootTables, block) -> lootTables.add(
                    block, RegistrateBlockLootTables.createOreDrop(block, MoreMaterialsItems.RUBY.get())
            ))
            .tag(BlockTags.NEEDS_IRON_TOOL)
            .tag(Tags.Blocks.ORES)
            .transform(tagBlockAndItem("ores/ruby", "ores_in_ground/stone"))
            .tag(Tags.Items.ORES)
            .build()
            .register();

    public static final BlockEntry<DropExperienceBlock> DEEPSLATE_RUBY_ORE = REGISTRATE
            .block("deepslate_ruby_ore", properties -> new DropExperienceBlock(properties, UniformInt.of(3, 7)))
            .initialProperties(RUBY_ORE)
            .properties(properties -> properties
                    .color(MaterialColor.DEEPSLATE)
                    .requiresCorrectToolForDrops()
                    .strength(4.5F, 3.0F)
                    .sound(SoundType.DEEPSLATE)
            )
            .transform(pickaxeOnly())
            .loot((lootTables, block) -> lootTables.add(
                    block, RegistrateBlockLootTables.createOreDrop(block, MoreMaterialsItems.RUBY.get())
            ))
            .tag(BlockTags.NEEDS_IRON_TOOL)
            .tag(Tags.Blocks.ORES)
            .transform(tagBlockAndItem("ores/ruby", "ores_in_ground/deepslate"))
            .tag(Tags.Items.ORES)
            .build()
            .register();

    public static final BlockEntry<Block> RUBY_BLOCK = REGISTRATE
            .block("ruby_block", Block::new)
            .initialProperties(Material.METAL, MaterialColor.COLOR_RED)
            .properties(properties -> properties
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)
            )
            .transform(pickaxeOnly())
            .tag(BlockTags.NEEDS_IRON_TOOL)
            .tag(Tags.Blocks.STORAGE_BLOCKS)
            .tag(BlockTags.BEACON_BASE_BLOCKS)
            .transform(tagBlockAndItem("storage_blocks/ruby"))
            .tag(Tags.Items.STORAGE_BLOCKS)
            .build()
            .lang("Block of Ruby")
            .register();

    public static void register() {
    }
}
