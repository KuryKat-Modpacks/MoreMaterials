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

import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class RegistryUtils {

    public static <T> RegistryObject<T> getRegistryObjectByName(
            ArrayList<RegistryObject<T>> registryList,
            String name
    ) {
        for (RegistryObject<T> registryObject : registryList) {
            if (registryObject.getId().getPath().equals(name)) {
                return registryObject;
            }
        }
        return null;
    }
}
