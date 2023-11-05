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

package dev.kurykat.morematerials.infrastructure.data;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.kurykat.morematerials.MoreMaterials;
import dev.kurykat.morematerials.tags.MoreMaterialsBlockTags;
import dev.kurykat.morematerials.tags.MoreMaterialsEntityTags;
import dev.kurykat.morematerials.tags.MoreMaterialsFluidTags;
import dev.kurykat.morematerials.tags.MoreMaterialsItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags;

public class MoreMaterialsRegistrateTags {
    public static void addGenerators() {
        Registrate REGISTRATE = MoreMaterials.getRegistrate();
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, MoreMaterialsRegistrateTags::generateBlockTags);
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, MoreMaterialsRegistrateTags::generateItemTags);
        REGISTRATE.addDataGenerator(ProviderType.FLUID_TAGS, MoreMaterialsRegistrateTags::generateFluidTags);
        REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, MoreMaterialsRegistrateTags::generateEntityTags);
    }

    private static void generateBlockTags(RegistrateTagsProvider<Block> provider) {

        // VALIDATE

        for (MoreMaterialsBlockTags tag : MoreMaterialsBlockTags.values()) {
            if (tag.alwaysDataGen) {
                provider.getOrCreateRawBuilder(tag.tag);
            }
        }
    }

    private static void generateItemTags(RegistrateTagsProvider<Item> provider) {
        provider.tag(ItemTags.BEACON_PAYMENT_ITEMS)
                .addTag(MoreMaterialsItemTags.GEMS.tag)
                .addTag(MoreMaterialsItemTags.INGOTS.tag);

        provider.tag(Tags.Items.GEMS)
                .addTag(MoreMaterialsItemTags.GEMS.tag);

        provider.tag(Tags.Items.INGOTS)
                .addTag(MoreMaterialsItemTags.INGOTS.tag);

        provider.tag(Tags.Items.RAW_MATERIALS)
                .addTag(MoreMaterialsItemTags.RAW_MATERIALS.tag);

        // VALIDATE

        for (MoreMaterialsItemTags tag : MoreMaterialsItemTags.values()) {
            if (tag.alwaysDataGen) {
                provider.getOrCreateRawBuilder(tag.tag);
            }
        }
    }

    private static void generateFluidTags(RegistrateTagsProvider<Fluid> provider) {

        // VALIDATE

        for (MoreMaterialsFluidTags tag : MoreMaterialsFluidTags.values()) {
            if (tag.alwaysDataGen) {
                provider.getOrCreateRawBuilder(tag.tag);
            }
        }
    }

    private static void generateEntityTags(RegistrateTagsProvider<EntityType<?>> provider) {

        // VALIDATE

        for (MoreMaterialsEntityTags tag : MoreMaterialsEntityTags.values()) {
            if (tag.alwaysDataGen) {
                provider.getOrCreateRawBuilder(tag.tag);
            }
        }
    }
}
