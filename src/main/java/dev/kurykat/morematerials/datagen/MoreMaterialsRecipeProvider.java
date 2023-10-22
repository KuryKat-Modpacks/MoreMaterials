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
import dev.kurykat.morematerials.Constants;
import dev.kurykat.morematerials.init.MoreMaterialsItemInit;
import dev.kurykat.morematerials.utils.RegistryUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class MoreMaterialsRecipeProvider extends RecipeProvider {
    protected static final ImmutableList<ItemLike> RUBY_SMELTABLES = ImmutableList.of(
            Objects.requireNonNull(RegistryUtils.getRegistryObjectByName(
                    MoreMaterialsItemInit.SIMPLE_ITEMS,
                    "ruby_ore"
            )).get(),
            Objects.requireNonNull(RegistryUtils.getRegistryObjectByName(
                    MoreMaterialsItemInit.SIMPLE_ITEMS,
                    "deepslate_ruby_ore"
            )).get()
    );

    public MoreMaterialsRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    public @NotNull String getName() {
        return Constants.MOD_NAME + super.getName();
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        nineBlockStorageRecipes(
                recipeConsumer,
                MoreMaterialsItemInit.RUBY.get(),
                Objects.requireNonNull(
                        RegistryUtils.getRegistryObjectByName(
                                MoreMaterialsItemInit.SIMPLE_ITEMS,
                                "ruby_block"
                        )
                ).get()
        );

        oreSmelting(recipeConsumer, RUBY_SMELTABLES, MoreMaterialsItemInit.RUBY.get(), 1.0F, 200, "ruby");
        oreBlasting(recipeConsumer, RUBY_SMELTABLES, MoreMaterialsItemInit.RUBY.get(), 1.0F, 200, "ruby");
    }
}
