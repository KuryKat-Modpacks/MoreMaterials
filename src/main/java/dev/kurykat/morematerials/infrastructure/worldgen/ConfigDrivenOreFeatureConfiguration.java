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

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;

import java.util.List;

public class ConfigDrivenOreFeatureConfiguration extends BaseConfigDrivenOreFeatureConfiguration {
    public static final Codec<ConfigDrivenOreFeatureConfiguration> CODEC = RecordCodecBuilder
            .create(instance -> instance
                    .group(
                            OreFeatureConfigEntry.CODEC
                                    .fieldOf("entry")
                                    .forGetter(config -> config.entry),
                            Codec.floatRange(0.0F, 1.0F)
                                    .fieldOf("discard_chance_on_air_exposure")
                                    .forGetter(config -> config.discardChanceOnAirExposure),
                            Codec.list(TargetBlockState.CODEC)
                                    .fieldOf("targets")
                                    .forGetter(config -> config.targetStates)
                    ).apply(instance, ConfigDrivenOreFeatureConfiguration::new));

    private final List<TargetBlockState> targetStates;

    public ConfigDrivenOreFeatureConfiguration(OreFeatureConfigEntry entry, float discardChanceOnAirExposure, List<TargetBlockState> targetStates) {
        super(entry, discardChanceOnAirExposure);
        this.targetStates = targetStates;
    }

    public List<TargetBlockState> getTargetStates() {
        return targetStates;
    }
}
