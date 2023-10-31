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

package dev.kurykat.morematerials.infrastructure.config;

import dev.kurykat.morematerials.foundation.config.BaseConfig;

public class MoreMaterialsCommonConfig extends BaseConfig {

    public final MoreMaterialsWorldGenConfig worldGenConfig = nested(0, MoreMaterialsWorldGenConfig::new, Comments.worldGen);

    @Override
    public String getName() {
        return "common";
    }


    private static class Comments {
        static String worldGen = "Modify MoreMaterials' Impact on your terrain.";
    }
}
