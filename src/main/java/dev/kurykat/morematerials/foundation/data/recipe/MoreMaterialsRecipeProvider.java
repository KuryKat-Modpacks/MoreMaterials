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

package dev.kurykat.morematerials.foundation.data.recipe;

import dev.kurykat.morematerials.MoreMaterialsConstants;
import dev.kurykat.morematerials.tags.MoreMaterialsTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class MoreMaterialsRecipeProvider extends RecipeProvider {
    protected final List<GeneratedRecipe> ALL = new ArrayList<>();

    public MoreMaterialsRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ALL.forEach(generatedRecipe -> generatedRecipe.register(consumer));
        MoreMaterialsConstants.LOGGER.info(getName() + " registered " + ALL.size() + " recipe" + (ALL.size() == 1 ? "" : "s"));
    }

    protected GeneratedRecipe register(GeneratedRecipe recipe) {
        ALL.add(recipe);
        return recipe;
    }

    @FunctionalInterface
    public interface GeneratedRecipe {
        void register(Consumer<FinishedRecipe> consumer);
    }

    protected static class Marker {
    }

    protected static class I {
        static TagKey<Item> ruby() {
            return MoreMaterialsTags.forgeItemTag("gems/ruby");
        }

        static TagKey<Item> rubyBlock() {
            return MoreMaterialsTags.forgeItemTag("storage_blocks/ruby");
        }

        static TagKey<Item> redstone() {
            return Tags.Items.DUSTS_REDSTONE;
        }

        static TagKey<Item> planks() {
            return ItemTags.PLANKS;
        }

        static TagKey<Item> woodSlab() {
            return ItemTags.WOODEN_SLABS;
        }

        static TagKey<Item> stone() {
            return Tags.Items.STONE;
        }

        static TagKey<Item> gold() {
            return Tags.Items.INGOTS_GOLD;
        }

        static TagKey<Item> goldNugget() {
            return Tags.Items.NUGGETS_GOLD;
        }

        static TagKey<Item> iron() {
            return Tags.Items.INGOTS_IRON;
        }

        static TagKey<Item> ironNugget() {
            return Tags.Items.NUGGETS_IRON;
        }

        static ItemLike copperBlock() {
            return Items.COPPER_BLOCK;
        }

        static ItemLike copper() {
            return Items.COPPER_INGOT;
        }

        static Ingredient netherite() {
            return Ingredient.of(MoreMaterialsTags.forgeItemTag("ingots/netherite"));
        }
    }
}
