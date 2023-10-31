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
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import static dev.kurykat.morematerials.tags.MoreMaterialsTags.Namespace;
import static dev.kurykat.morematerials.tags.MoreMaterialsTags.Namespace.MOD;
import static dev.kurykat.morematerials.tags.MoreMaterialsTags.optionalTag;

public enum MoreMaterialsEntityTags {
    ;

    public final TagKey<EntityType<?>> tag;
    public final boolean alwaysDataGen;

    MoreMaterialsEntityTags() {
        this(MOD);
    }

    MoreMaterialsEntityTags(Namespace namespace) {
        this(namespace, namespace.optionalByDefault, namespace.alwaysDataGenByDefault);
    }

    MoreMaterialsEntityTags(Namespace namespace, String path) {
        this(namespace, path, namespace.optionalByDefault, namespace.alwaysDataGenByDefault);
    }

    MoreMaterialsEntityTags(Namespace namespace, boolean optional, boolean alwaysDataGen) {
        this(namespace, null, optional, alwaysDataGen);
    }

    MoreMaterialsEntityTags(Namespace namespace, String path, boolean optional, boolean alwaysDataGen) {
        ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
        if (optional) {
            tag = optionalTag(ForgeRegistries.ENTITY_TYPES, id);
        } else {
            tag = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, id);
        }
        this.alwaysDataGen = alwaysDataGen;
    }

    public boolean matches(Entity entity) {
        return entity.getType().is(tag);
    }

    public static void init() {
    }
}