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

package dev.kurykat.morematerials.tags;

import dev.kurykat.morematerials.foundation.util.Lang;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import static dev.kurykat.morematerials.tags.MoreMaterialsTags.Namespace;
import static dev.kurykat.morematerials.tags.MoreMaterialsTags.Namespace.MOD;
import static dev.kurykat.morematerials.tags.MoreMaterialsTags.optionalTag;


public enum MoreMaterialsItemTags {
    GEMS,
    INGOTS,
    RAW_MATERIALS;

    public final TagKey<Item> tag;
    public final boolean alwaysDataGen;

    MoreMaterialsItemTags() {
        this(MOD);
    }

    MoreMaterialsItemTags(Namespace namespace) {
        this(namespace, namespace.optionalByDefault, namespace.alwaysDataGenByDefault);
    }

    MoreMaterialsItemTags(Namespace namespace, String path) {
        this(namespace, path, namespace.optionalByDefault, namespace.alwaysDataGenByDefault);
    }

    MoreMaterialsItemTags(Namespace namespace, boolean optional, boolean alwaysDataGen) {
        this(namespace, null, optional, alwaysDataGen);
    }

    MoreMaterialsItemTags(Namespace namespace, String path, boolean optional, boolean alwaysDataGen) {
        ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
        if (optional) {
            tag = optionalTag(ForgeRegistries.ITEMS, id);
        } else {
            tag = ItemTags.create(id);
        }
        this.alwaysDataGen = alwaysDataGen;
    }

    @SuppressWarnings("deprecation")
    public boolean matches(Item item) {
        return item.builtInRegistryHolder()
                .is(tag);
    }

    public boolean matches(ItemStack stack) {
        return stack.is(tag);
    }

    public static void init() {
    }
}
