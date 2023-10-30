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

import java.util.List;

public class ConfigDrivenLayeredOreFeatureConfiguration extends BaseConfigDrivenOreFeatureConfiguration {
    public static final Codec<ConfigDrivenLayeredOreFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                OreFeatureConfigEntry.CODEC
                        .fieldOf("entry")
                        .forGetter(config -> config.entry),
                Codec.floatRange(0.0F, 1.0F)
                        .fieldOf("discard_chance_on_air_exposure")
                        .forGetter(config -> config.discardChanceOnAirExposure),
                Codec.list(LayerPattern.CODEC)
                        .fieldOf("layer_patterns")
                        .forGetter(config -> config.layerPatterns)
        ).apply(instance, ConfigDrivenLayeredOreFeatureConfiguration::new);
    });

    private final List<LayerPattern> layerPatterns;

    public ConfigDrivenLayeredOreFeatureConfiguration(OreFeatureConfigEntry entry, float discardChanceOnAirExposure, List<LayerPattern> layerPatterns) {
        super(entry, discardChanceOnAirExposure);
        this.layerPatterns = layerPatterns;
    }

    public List<LayerPattern> getLayerPatterns() {
        return layerPatterns;
    }
}