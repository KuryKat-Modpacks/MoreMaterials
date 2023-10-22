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

package dev.kurykat.morematerials.datagen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import dev.kurykat.morematerials.Constants;
import dev.kurykat.morematerials.datagen.loot.MoreMaterialsBlockLootTables;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MoreMaterialsLootTableProvider extends LootTableProvider {
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> subProviders = ImmutableList.of(
            Pair.of(MoreMaterialsBlockLootTables::new, LootContextParamSets.BLOCK)
    );

    public MoreMaterialsLootTableProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    public @NotNull String getName() {
        return Constants.MOD_NAME + super.getName();
    }

    @Override
    protected @NotNull List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return subProviders;
    }

    @Override
    protected void validate(@NotNull Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationTracker) {
        final Set<ResourceLocation> lootTablesIDs = BuiltInLootTables
                .all()
                .stream()
                .filter(lootTable -> lootTable.getNamespace().equals(Constants.MOD_ID))
                .collect(Collectors.toSet());

        for (final ResourceLocation id : Sets.difference(lootTablesIDs, map.keySet())) {
            validationTracker.reportProblem("Missing loot table: " + id);
        }

        map.forEach((id, lootTable) -> LootTables.validate(validationTracker, id, lootTable));
    }
}
