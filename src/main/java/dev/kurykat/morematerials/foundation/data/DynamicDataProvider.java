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

package dev.kurykat.morematerials.foundation.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import dev.kurykat.morematerials.MoreMaterials;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public class DynamicDataProvider<T> implements DataProvider {
    private final DataGenerator generator;
    private final String name;
    private final RegistryAccess registryAccess;
    private final RegistryAccess.RegistryData<T> registryData;
    private final Map<ResourceLocation, T> values;

    public DynamicDataProvider(DataGenerator generator, String name, RegistryAccess registryAccess, RegistryAccess.RegistryData<T> registryData, Map<ResourceLocation, T> values) {
        this.generator = generator;
        this.name = name;
        this.registryAccess = registryAccess;
        this.registryData = registryData;
        this.values = values;
    }

    @Nullable
    public static <T> DynamicDataProvider<T> create(DataGenerator generator, String name, RegistryAccess registryAccess, ResourceKey<? extends Registry<T>> registryKey, Map<ResourceLocation, T> values) {
        @SuppressWarnings("unchecked")
        RegistryAccess.RegistryData<T> registryData = (RegistryAccess.RegistryData<T>) RegistryAccess.REGISTRIES.get(registryKey);
        if (registryData == null) {
            return null;
        }
        return new DynamicDataProvider<>(generator, name, registryAccess, registryData, values);
    }

    @Override
    public void run(CachedOutput cache) throws IOException {
        Path path = generator.getOutputFolder();
        DynamicOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, registryAccess);

        dumpValues(path, cache, ops, registryData.key(), values, registryData.codec());
    }

    private void dumpValues(Path rootPath, CachedOutput cache, DynamicOps<JsonElement> ops, ResourceKey<? extends Registry<T>> registryKey, Map<ResourceLocation, T> values, Encoder<T> encoder) {
        DataGenerator.PathProvider pathProvider = generator.createPathProvider(DataGenerator.Target.DATA_PACK, ForgeHooks.prefixNamespace(registryKey.location()));

        for (Map.Entry<ResourceLocation, T> entry : values.entrySet()) {
            dumpValue(pathProvider.json(entry.getKey()), cache, ops, encoder, entry.getValue());
        }
    }

    private void dumpValue(Path path, CachedOutput cache, DynamicOps<JsonElement> ops, Encoder<T> encoder, T value) {
        try {
            Optional<JsonElement> optional = encoder.encodeStart(ops, value).resultOrPartial(
                    (message) -> MoreMaterials.LOGGER.error("Couldn't serialize element {}: {}", path, message)
            );
            if (optional.isPresent()) {
                DataProvider.saveStable(cache, optional.get(), path);
            }
        } catch (IOException e) {
            MoreMaterials.LOGGER.error("Couldn't save element {}", path, e);
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
