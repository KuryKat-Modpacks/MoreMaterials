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

import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "morematerials";
    public static final String MOD_NAME = "MoreMaterials";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final MoreMaterialsCreativeModeTab CREATIVE_TAB = new MoreMaterialsCreativeModeTab(MOD_ID);

    public static Item.Properties DEFAULT_ITEM_PROPS = new Item.Properties().tab(CREATIVE_TAB);
}
