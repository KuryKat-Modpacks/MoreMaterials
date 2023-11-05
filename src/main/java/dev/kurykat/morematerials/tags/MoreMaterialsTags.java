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

import dev.kurykat.morematerials.MoreMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.versions.forge.ForgeVersion;

import java.util.Collections;

public class MoreMaterialsTags {
    public static <T> TagKey<T> optionalTag(IForgeRegistry<T> registry, ResourceLocation id) {
        return registry.tags().createOptionalTagKey(id, Collections.emptySet());
    }

    public static <T> TagKey<T> forgeTag(IForgeRegistry<T> registry, String path) {
        return optionalTag(registry, new ResourceLocation(ForgeVersion.MOD_ID, path));
    }

    public static TagKey<Block> forgeBlockTag(String path) {
        return forgeTag(ForgeRegistries.BLOCKS, path);
    }

    public static TagKey<Item> forgeItemTag(String path) {
        return forgeTag(ForgeRegistries.ITEMS, path);
    }

    public static TagKey<Fluid> forgeFluidTag(String path) {
        return forgeTag(ForgeRegistries.FLUIDS, path);
    }

    public enum Namespace {
        MOD(MoreMaterials.MOD_ID, false, true),
        FORGE(ForgeVersion.MOD_ID);

        public final String id;
        public final boolean optionalByDefault;
        public final boolean alwaysDataGenByDefault;

        Namespace(String id) {
            this(id, true, false);
        }

        Namespace(String id, boolean optionalByDefault, boolean alwaysDataGenByDefault) {
            this.id = id;
            this.optionalByDefault = optionalByDefault;
            this.alwaysDataGenByDefault = alwaysDataGenByDefault;
        }
    }

    public static void init() {
        MoreMaterialsBlockTags.init();
        MoreMaterialsItemTags.init();
        MoreMaterialsFluidTags.init();
        MoreMaterialsEntityTags.init();
    }
}
