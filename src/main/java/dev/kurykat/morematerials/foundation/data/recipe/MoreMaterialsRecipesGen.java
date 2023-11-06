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

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import dev.kurykat.morematerials.MoreMaterials;
import dev.kurykat.morematerials.foundation.util.RegisteredObjects;
import dev.kurykat.morematerials.registries.MoreMaterialsBlocks;
import dev.kurykat.morematerials.registries.MoreMaterialsItems;
import dev.kurykat.morematerials.tags.MoreMaterialsTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class MoreMaterialsRecipesGen extends MoreMaterialsRecipeProvider {

    private final Marker MATERIALS = enterFolder("materials");

    GeneratedRecipe RUBY_COMPACTING = materialCompacting(
            ImmutableList.of(MoreMaterialsItems.RUBY, MoreMaterialsBlocks.RUBY_BLOCK),
            ImmutableList.of(I::ruby, I::rubyBlock)
    );

    GeneratedRecipe ALEXANDRITE_COMPACTING = materialCompacting(
            ImmutableList.of(MoreMaterialsItems.ALEXANDRITE, MoreMaterialsBlocks.ALEXANDRITE_BLOCK),
            ImmutableList.of(I::alexandrite, I::alexandriteBlock)
    );

    GeneratedRecipe CELESLAR_COMPACTING = materialCompacting(
            ImmutableList.of(MoreMaterialsItems.CELESLAR_INGOT, MoreMaterialsBlocks.CELESLAR_BLOCK),
            ImmutableList.of(I::celeslar, I::celeslarBlock)
    );

    GeneratedRecipe RAW_CELESLAR_COMPACTING = materialCompacting(
            ImmutableList.of(MoreMaterialsItems.RAW_CELESLAR, MoreMaterialsBlocks.RAW_CELESLAR_BLOCK),
            ImmutableList.of(I::rawCeleslar, I::rawCeleslarBlock)
    );

    private final Marker COOKING = enterFolder("/");

    GeneratedRecipe RUBY_ORE = blastOreTag(MoreMaterialsItems.RUBY::get, () -> MoreMaterialsTags.forgeItemTag("ores/ruby"));

    GeneratedRecipe ALEXANDRITE_ORE = blastOreTag(MoreMaterialsItems.ALEXANDRITE::get, () -> MoreMaterialsTags.forgeItemTag("ores/alexandrite"));

    GeneratedRecipe CELESLAR_ORE = blastOreTag(MoreMaterialsItems.CELESLAR_INGOT::get, () -> MoreMaterialsTags.forgeItemTag("ores/celeslar"));

    GeneratedRecipe RAW_CELESLAR_ORE = blastRawOreTag(MoreMaterialsItems.CELESLAR_INGOT::get, () -> MoreMaterialsTags.forgeItemTag("raw_materials/celeslar"));

    /*
     * End of recipe list
     */
    String currentFolder = "";

    Marker enterFolder(String folder) {
        currentFolder = folder;
        return new Marker();
    }

    GeneratedRecipeBuilder create(Supplier<ItemLike> result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }

    GeneratedRecipeBuilder create(ResourceLocation result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }

    GeneratedRecipeBuilder create(ItemProviderEntry<? extends ItemLike> result) {
        return create(result::get);
    }

    GeneratedRecipe createSpecial(Supplier<? extends SimpleRecipeSerializer<?>> serializer, String recipeType, String path) {
        ResourceLocation location = MoreMaterials.asResource(recipeType + "/" + currentFolder + "/" + path);
        return register(consumer -> {
            SpecialRecipeBuilder builder = SpecialRecipeBuilder.special(serializer.get());
            builder.save(consumer, location.toString());
        });
    }

    GeneratedRecipe materialCompacting(List<ItemProviderEntry<? extends ItemLike>> variants, List<Supplier<TagKey<Item>>> ingredients) {
        GeneratedRecipe result = null;
        for (int i = 0; i + 1 < variants.size(); i++) {
            ItemProviderEntry<? extends ItemLike> currentEntry = variants.get(i);
            ItemProviderEntry<? extends ItemLike> nextEntry = variants.get(i + 1);
            Supplier<TagKey<Item>> currentIngredient = ingredients.get(i);
            Supplier<TagKey<Item>> nextIngredient = ingredients.get(i + 1);

            result = create(nextEntry)
                    .withSuffix("_from_compacting")
                    .unlockedBy(currentEntry::get)
                    .viaShaped(b -> b
                            .pattern("###")
                            .pattern("###")
                            .pattern("###")
                            .define('#', currentIngredient.get()));

            result = create(currentEntry).returns(9)
                    .withSuffix("_from_decompacting")
                    .unlockedBy(nextEntry::get)
                    .viaShapeless(b -> b.requires(nextIngredient.get()));
        }
        return result;
    }

    GeneratedRecipe blastOre(Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> ingredient) {
        return create(result::get)
                .withSuffix("_from_ore")
                .viaCooking(ingredient::get)
                .rewardXP(1)
                .inBlastFurnace();
    }

    GeneratedRecipe blastOreTag(Supplier<? extends ItemLike> result, Supplier<TagKey<Item>> tag) {
        return create(result::get)
                .withSuffix("_from_ore")
                .viaCookingTag(tag::get)
                .rewardXP(1)
                .inBlastFurnace();
    }

    GeneratedRecipe blastRawOre(Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> ingredient) {
        return create(result::get)
                .withSuffix("_from_raw_ore")
                .viaCooking(ingredient::get)
                .rewardXP(.7F)
                .inBlastFurnace();
    }

    GeneratedRecipe blastRawOreTag(Supplier<? extends ItemLike> result, Supplier<TagKey<Item>> tag) {
        return create(result::get)
                .withSuffix("_from_raw_ore")
                .viaCookingTag(tag::get)
                .rewardXP(.7F)
                .inBlastFurnace();
    }

    class GeneratedRecipeBuilder {
        private String path;
        private String suffix;
        private Supplier<? extends ItemLike> result;
        private ResourceLocation compatDataGenOutput;
        List<ICondition> recipeConditions;
        private Supplier<ItemPredicate> unlockedBy;
        private int amount;

        private GeneratedRecipeBuilder(String path) {
            this.path = path;
            this.recipeConditions = new ArrayList<>();
            this.suffix = "";
            this.amount = 1;
        }

        public GeneratedRecipeBuilder(String path, Supplier<? extends ItemLike> result) {
            this(path);
            this.result = result;
        }

        public GeneratedRecipeBuilder(String path, ResourceLocation result) {
            this(path);
            this.compatDataGenOutput = result;
        }

        GeneratedRecipeBuilder returns(int amount) {
            this.amount = amount;
            return this;
        }

        GeneratedRecipeBuilder unlockedBy(Supplier<? extends ItemLike> item) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(item.get())
                    .build();
            return this;
        }

        GeneratedRecipeBuilder unlockedByTag(Supplier<TagKey<Item>> tag) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(tag.get())
                    .build();
            return this;
        }

        GeneratedRecipeBuilder whenModLoaded(String modid) {
            return withCondition(new ModLoadedCondition(modid));
        }

        GeneratedRecipeBuilder whenModMissing(String modid) {
            return withCondition(new NotCondition(new ModLoadedCondition(modid)));
        }

        GeneratedRecipeBuilder withCondition(ICondition condition) {
            recipeConditions.add(condition);
            return this;
        }

        GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        GeneratedRecipe viaShaped(UnaryOperator<ShapedRecipeBuilder> builder) {
            return register(consumer -> {
                ShapedRecipeBuilder b = builder.apply(ShapedRecipeBuilder.shaped(result.get(), amount));
                if (unlockedBy != null) {
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                }
                b.save(consumer, createLocation("crafting"));
            });
        }

        GeneratedRecipe viaShapeless(UnaryOperator<ShapelessRecipeBuilder> builder) {
            return register(consumer -> {
                ShapelessRecipeBuilder b = builder.apply(ShapelessRecipeBuilder.shapeless(result.get(), amount));
                if (unlockedBy != null) {
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                }
                b.save(consumer, createLocation("crafting"));
            });
        }

        private ResourceLocation createSimpleLocation(String recipeType) {
            return MoreMaterials.asResource(recipeType + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation createLocation(String recipeType) {
            return MoreMaterials.asResource(recipeType + "/" + path + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation getRegistryName() {
            return compatDataGenOutput == null ? RegisteredObjects.getKeyOrThrow(result.get().asItem()) : compatDataGenOutput;
        }

        GeneratedCookingRecipeBuilder viaCooking(Supplier<? extends ItemLike> item) {
            return unlockedBy(item).viaCookingIngredient(() -> Ingredient.of(item.get()));
        }

        GeneratedCookingRecipeBuilder viaCookingTag(Supplier<TagKey<Item>> tag) {
            return unlockedByTag(tag).viaCookingIngredient(() -> Ingredient.of(tag.get()));
        }

        GeneratedCookingRecipeBuilder viaCookingIngredient(Supplier<Ingredient> ingredient) {
            return new GeneratedCookingRecipeBuilder(ingredient);
        }

        class GeneratedCookingRecipeBuilder {

            private Supplier<Ingredient> ingredient;
            private float exp;
            private int cookingTime;

            private final SimpleCookingSerializer<?> FURNACE = RecipeSerializer.SMELTING_RECIPE;
            private final SimpleCookingSerializer<?> SMOKER = RecipeSerializer.SMOKING_RECIPE;
            private final SimpleCookingSerializer<?> BLAST = RecipeSerializer.BLASTING_RECIPE;
            private final SimpleCookingSerializer<?> CAMPFIRE = RecipeSerializer.CAMPFIRE_COOKING_RECIPE;

            GeneratedCookingRecipeBuilder(Supplier<Ingredient> ingredient) {
                this.ingredient = ingredient;
                cookingTime = 200;
                exp = 0;
            }

            GeneratedCookingRecipeBuilder forDuration(int duration) {
                cookingTime = duration;
                return this;
            }

            GeneratedCookingRecipeBuilder rewardXP(float xp) {
                exp = xp;
                return this;
            }

            GeneratedRecipe inFurnace() {
                return inFurnace(b -> b);
            }

            GeneratedRecipe inFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                return create(FURNACE, builder, 1);
            }

            GeneratedRecipe inSmoker() {
                return inSmoker(b -> b);
            }

            GeneratedRecipe inSmoker(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                create(FURNACE, builder, 1);
                create(CAMPFIRE, builder, 3);
                return create(SMOKER, builder, .5f);
            }

            GeneratedRecipe inBlastFurnace() {
                return inBlastFurnace(b -> b);
            }

            GeneratedRecipe inBlastFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                create(FURNACE, builder, 1);
                return create(BLAST, builder, .5f);
            }

            private GeneratedRecipe create(
                    SimpleCookingSerializer<?> serializer,
                    UnaryOperator<SimpleCookingRecipeBuilder> builder,
                    float cookingTimeModifier
            ) {
                return register(consumer -> {
                    boolean isOtherMod = compatDataGenOutput != null;

                    SimpleCookingRecipeBuilder subBuilder = builder.apply(
                            SimpleCookingRecipeBuilder.cooking(
                                    ingredient.get(),
                                    isOtherMod ? Items.DIRT : result.get(),
                                    exp,
                                    (int) (cookingTime * cookingTimeModifier),
                                    serializer
                            )
                    );
                    if (unlockedBy != null) {
                        subBuilder.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                    }
                    subBuilder.save(
                            result -> consumer.accept(
                                    isOtherMod ?
                                            new ModdedCookingRecipeResult(result, compatDataGenOutput, recipeConditions)
                                            : result
                            ),
                            createSimpleLocation(
                                    RegisteredObjects.getKeyOrThrow(serializer).getPath()
                            )
                    );
                });
            }
        }
    }

    public MoreMaterialsRecipesGen(DataGenerator generator) {
        super(generator);
    }

    @Override
    public @NotNull String getName() {
        return MoreMaterials.MOD_NAME + super.getName();
    }

    private static class ModdedCookingRecipeResult implements FinishedRecipe {
        private FinishedRecipe wrapped;
        private ResourceLocation outputOverride;
        private List<ICondition> conditions;

        public ModdedCookingRecipeResult(
                FinishedRecipe wrapped,
                ResourceLocation outputOverride,
                List<ICondition> conditions
        ) {
            this.wrapped = wrapped;
            this.outputOverride = outputOverride;
            this.conditions = conditions;
        }

        @Override
        public ResourceLocation getId() {
            return wrapped.getId();
        }

        @Override
        public RecipeSerializer<?> getType() {
            return wrapped.getType();
        }

        @Override
        public JsonObject serializeAdvancement() {
            return wrapped.serializeAdvancement();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return wrapped.getAdvancementId();
        }

        @Override
        public void serializeRecipeData(JsonObject object) {
            wrapped.serializeRecipeData(object);
            object.addProperty("result", outputOverride.toString());

            JsonArray conditions = new JsonArray();
            this.conditions.forEach(condition -> conditions.add(CraftingHelper.serialize(condition)));
            object.add("conditions", conditions);
        }
    }
}
