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

import dev.kurykat.morematerials.MoreMaterials;
import dev.kurykat.morematerials.foundation.config.BaseConfig;
import dev.kurykat.morematerials.infrastructure.worldgen.MoreMaterialsOreFeatureConfigEntries;
import net.minecraftforge.common.ForgeConfigSpec;

public class MoreMaterialsWorldGenConfig extends BaseConfig {
    public static final int FORCED_UPDATE_VERSION = 1;

    public final BoolConfig disable = boolConfig(false, "disableWorldGen", Comments.disable);

    @Override
    public void registerAll(ForgeConfigSpec.Builder builder) {
        super.registerAll(builder);
        MoreMaterialsOreFeatureConfigEntries.fillConfig(builder, MoreMaterials.MOD_ID);
    }

    @Override
    public String getName() {
        return "worldgen.v" + FORCED_UPDATE_VERSION;
    }

    private static class Comments {
        static String disable = "Prevents all WorldGen added by MoreMaterials from taking effect";
    }
}
