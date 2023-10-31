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
import dev.kurykat.morematerials.infrastructure.config.MoreMaterialsConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConfigDrivenPlacement extends PlacementModifier {
    public static final Codec<ConfigDrivenPlacement> CODEC = RecordCodecBuilder
            .create(instance -> instance
                    .group(
                            OreFeatureConfigEntry.CODEC
                                    .fieldOf("entry")
                                    .forGetter(ConfigDrivenPlacement::getEntry)
                    ).apply(instance, ConfigDrivenPlacement::new));

    private final OreFeatureConfigEntry entry;

    public ConfigDrivenPlacement(OreFeatureConfigEntry entry) {
        this.entry = entry;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext placementContext, RandomSource random, BlockPos blockPos) {
        int count = getCount(getFrequency(), random);
        if (count == 0) {
            return Stream.empty();
        }

        int minY = getMinY();
        int maxY = getMaxY();

        return IntStream.range(0, count)
                .mapToObj(i -> blockPos)
                .map(pos -> {
                    int x = random.nextInt(16) + pos.getX();
                    int y = Mth.randomBetweenInclusive(random, minY, maxY);
                    int z = random.nextInt(16) + pos.getZ();
                    return new BlockPos(x, y, z);
                });
    }

    public int getCount(float frequency, RandomSource random) {
        int floored = Mth.floor(frequency);
        return floored + (random.nextFloat() < (frequency - floored) ? 1 : 0);
    }

    @Override
    public PlacementModifierType<?> type() {
        return MoreMaterialsPlacementModifiers.CONFIG_DRIVEN.get();
    }

    public OreFeatureConfigEntry getEntry() {
        return entry;
    }

    public float getFrequency() {
        if (MoreMaterialsConfigs.commonConfig().worldGenConfig.disable.get()) {
            return 0;
        }
        return entry.frequency.getFloat();
    }

    public int getMinY() {
        return entry.minHeight.get();
    }

    public int getMaxY() {
        return entry.maxHeight.get();
    }
}
