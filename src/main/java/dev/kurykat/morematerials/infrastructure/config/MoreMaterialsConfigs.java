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

package dev.kurykat.morematerials.infrastructure.config;

import dev.kurykat.morematerials.MoreMaterialsConstants;
import dev.kurykat.morematerials.foundation.config.BaseConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = MoreMaterialsConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoreMaterialsConfigs {
    private static final Map<ModConfig.Type, BaseConfig> CONFIGS = new EnumMap<>(ModConfig.Type.class);

    private static MoreMaterialsCommonConfig commonConfig;

    public static MoreMaterialsCommonConfig commonConfig() {
        return commonConfig;
    }

    private static <T extends BaseConfig> T register(Supplier<T> factory, ModConfig.Type side) {
        Pair<T, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(builder -> {
            T config = factory.get();
            config.registerAll(builder);
            return config;
        });

        T config = specPair.getLeft();
        config.specification = specPair.getRight();
        CONFIGS.put(side, config);
        return config;
    }

    public static void register(ModLoadingContext context) {
        commonConfig = register(MoreMaterialsCommonConfig::new, ModConfig.Type.COMMON);

        for (Map.Entry<ModConfig.Type, BaseConfig> pair : CONFIGS.entrySet()) {
            context.registerConfig(pair.getKey(), pair.getValue().specification);
        }
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        for (BaseConfig config : CONFIGS.values())
            if (config.specification == event.getConfig().getSpec()) {
                config.onLoad();
            }
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        for (BaseConfig config : CONFIGS.values())
            if (config.specification == event.getConfig().getSpec()) {
                config.onReload();
            }
    }
}
