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
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.function.Function;

public abstract class BaseConfigDrivenOreFeature<FC extends BaseConfigDrivenOreFeatureConfiguration> extends Feature<FC> {

    public BaseConfigDrivenOreFeature(Codec<FC> configCodec) {
        super(configCodec);
    }

    public boolean canPlaceOre(
            BlockState blockState,
            Function<BlockPos, BlockState> adjacentStateAccessor,
            RandomSource random,
            BaseConfigDrivenOreFeatureConfiguration config,
            OreConfiguration.TargetBlockState targetBlockState,
            BlockPos.MutableBlockPos mutableBlockPos
    ) {
        if (!targetBlockState.target.test(blockState, random)) {
            return false;
        }
        if (shouldSkipAirCheck(random, config.getDiscardChanceOnAirExposure())) {
            return true;
        }

        return !isAdjacentToAir(adjacentStateAccessor, mutableBlockPos);
    }

    protected boolean shouldSkipAirCheck(RandomSource random, float chance) {
        return chance <= 0 || (!(chance >= 1) && random.nextFloat() >= chance);
    }
}
