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

package dev.kurykat.morematerials.util;

public class ResourcesUtils {

    private static final String[] blockPrefixes = {"deepslate_", "nether_", "end_"};
    private static final String[] blockSuffixes = {"_ore", "_block"};

    public static String getResourceNameFromBlockName(String blockName) {
        return blockName
                .replaceAll("^(" + String.join("|", blockPrefixes) + ")", "")
                .replaceAll("(" + String.join("|", blockSuffixes) + ")$", "");
    }
}