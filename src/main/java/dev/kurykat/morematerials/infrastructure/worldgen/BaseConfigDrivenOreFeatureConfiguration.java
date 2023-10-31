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

package dev.kurykat.morematerials.infrastructure.worldgen;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class BaseConfigDrivenOreFeatureConfiguration implements FeatureConfiguration {
    protected final OreFeatureConfigEntry entry;
    protected final float discardChanceOnAirExposure;

    public BaseConfigDrivenOreFeatureConfiguration(OreFeatureConfigEntry entry, float discardChanceOnAirExposure) {
        this.entry = entry;
        this.discardChanceOnAirExposure = discardChanceOnAirExposure;
    }

    public OreFeatureConfigEntry getEntry() {
        return entry;
    }

    public int getClusterSize() {
        return entry.clusterSize.get();
    }

    public float getDiscardChanceOnAirExposure() {
        return discardChanceOnAirExposure;
    }
}