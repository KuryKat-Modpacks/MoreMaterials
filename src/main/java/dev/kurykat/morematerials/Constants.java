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

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "morematerials";
    public static final String MOD_NAME = "MoreMaterials";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return null;
        }
    };

    public static Item.Properties DEFAULT_ITEM_PROPS = new Item.Properties().tab(CREATIVE_TAB);

    public static class Tags {

        private static TagKey<Block> createBlockTag(String location) {
            return BlockTags.create(new ResourceLocation(MOD_ID, location));
        }

        private static TagKey<Block> createForgeBlockTag(String location) {
            return BlockTags.create(new ResourceLocation("forge", location));
        }

        private static TagKey<Block> createMinecraftBlockTag(String location) {
            return BlockTags.create(new ResourceLocation("minecraft", location));
        }

        private static TagKey<Item> createItemTag(String location) {
            return ItemTags.create(new ResourceLocation(MOD_ID, location));
        }

        private static TagKey<Item> createForgeItemTag(String location) {
            return ItemTags.create(new ResourceLocation("forge", location));
        }

        private static TagKey<Item> createMinecraftItemTag(String location) {
            return ItemTags.create(new ResourceLocation("minecraft", location));
        }
    }
}
