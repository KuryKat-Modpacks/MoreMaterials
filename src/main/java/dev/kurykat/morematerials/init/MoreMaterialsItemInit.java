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

package dev.kurykat.morematerials.init;

import dev.kurykat.morematerials.Constants;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.function.Supplier;

public class MoreMaterialsItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    public static final ArrayList<RegistryObject<Item>> SIMPLE_ITEMS = new ArrayList<>();
    public static final ArrayList<RegistryObject<Item>> RESOURCE_ITEMS = new ArrayList<>();
    public static final ArrayList<RegistryObject<Item>> HANDHELD_ITEMS = new ArrayList<>();

    public static final RegistryObject<Item> RUBY = registerResourceItem(
            "ruby",
            () -> new Item(
                    Constants.DEFAULT_ITEM_PROPS
            )
    );


    private static <T extends Item> RegistryObject<T> registerSimpleItem(String itemName, Supplier<T> itemSupplier) {
        RegistryObject<T> item = ITEMS.register(itemName, itemSupplier);
        SIMPLE_ITEMS.add((RegistryObject<Item>) item);
        return item;
    }

    private static <T extends Item> RegistryObject<T> registerResourceItem(String itemName, Supplier<T> itemSupplier) {
        RegistryObject<T> item = ITEMS.register(itemName, itemSupplier);
        RESOURCE_ITEMS.add((RegistryObject<Item>) item);
        return item;
    }

    private static <T extends Item> RegistryObject<T> registerHandheldItem(String itemName, Supplier<T> itemSupplier) {
        RegistryObject<T> item = ITEMS.register(itemName, itemSupplier);
        HANDHELD_ITEMS.add((RegistryObject<Item>) item);
        return item;
    }
}
