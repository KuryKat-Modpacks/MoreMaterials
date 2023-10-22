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

package dev.kurykat.morematerials.utils;

import java.util.ArrayList;

public class ResourcesUtils {
    private static final String[] orePrefixes = {"deepslate_", "nether_", "end_"};
    private static final String[] oreSuffixes = {"_ore", "_block"};
    private static final String[] tagPrefixes = {"ores/", "storage_blocks/raw_", "storage_blocks/"};

    public static String getResourceNameFromBlockName(String blockName) {
        return blockName
                .replaceAll("^(" + String.join("|", orePrefixes) + ")", "")
                .replaceAll("(" + String.join("|", oreSuffixes) + ")$", "");
    }

    public static String getResourceNameFromBlockTag(String blockTag) {
        return blockTag
                .replaceAll("^(" + String.join("|", tagPrefixes) + ")", "");
    }

    public static ArrayList<String> getBlockNameFromResourceName(String resourceName) {
        ArrayList<String> possibleNames = new ArrayList<>();

        for (String oreSuffix : oreSuffixes) {
            possibleNames.add(resourceName + oreSuffix);
            for (String orePrefix : orePrefixes) {
                possibleNames.add(
                        orePrefix + resourceName + oreSuffix
                );
            }
        }

        return possibleNames;
    }

    public static ArrayList<String> getBlockTagFromResourceName(String resourceName) {

        ArrayList<String> possibleTags = new ArrayList<>();

        for (String tagPrefix : tagPrefixes) {
            possibleTags.add(
                    tagPrefix + resourceName
            );
        }

        return possibleTags;
    }
}
