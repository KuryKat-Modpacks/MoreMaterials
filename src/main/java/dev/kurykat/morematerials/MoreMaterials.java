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

package dev.kurykat.morematerials;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.kurykat.morematerials.infrastructure.config.MoreMaterialsConfigs;
import dev.kurykat.morematerials.infrastructure.data.MoreMaterialsDataGen;
import dev.kurykat.morematerials.infrastructure.worldgen.MoreMaterialsBuiltinRegistration;
import dev.kurykat.morematerials.infrastructure.worldgen.MoreMaterialsFeatures;
import dev.kurykat.morematerials.infrastructure.worldgen.MoreMaterialsOreFeatureConfigEntries;
import dev.kurykat.morematerials.infrastructure.worldgen.MoreMaterialsPlacementModifiers;
import dev.kurykat.morematerials.registries.MoreMaterialsBlocks;
import dev.kurykat.morematerials.registries.MoreMaterialsItems;
import dev.kurykat.morematerials.tags.MoreMaterialsTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

@SuppressWarnings("unused")
@Mod(MoreMaterials.MOD_ID)
public class MoreMaterials {
    public static final String MOD_ID = "morematerials";
    public static final String MOD_NAME = "MoreMaterials";
    public static String VERSION;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final MoreMaterialsCreativeModeTab CREATIVE_TAB = new MoreMaterialsCreativeModeTab(MOD_ID);

    private static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MoreMaterials.MOD_ID));

    public static Registrate getRegistrate() {
        return REGISTRATE.get();
    }

    public MoreMaterials() {
        LOGGER.info("{} is Starting! Hello World!", MOD_NAME);
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        VERSION = getVersion(
                modLoadingContext
                        .getActiveContainer()
                        .getModInfo()
                        .getVersion()
        );
        LOGGER.info("Version {} is now running!!", VERSION);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        MoreMaterialsTags.init();
        MoreMaterialsItems.register();
        MoreMaterialsBlocks.register();

        MoreMaterialsOreFeatureConfigEntries.init();
        MoreMaterialsFeatures.register(modEventBus);
        MoreMaterialsPlacementModifiers.register(modEventBus);
        MoreMaterialsBuiltinRegistration.register(modEventBus);

        MoreMaterialsConfigs.register(modLoadingContext);

        modEventBus.addListener(EventPriority.LOWEST, MoreMaterialsDataGen::gatherData);
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private static String getVersion(ArtifactVersion version) {
        String versionQualifier = version.getQualifier();

        if (versionQualifier != null) {
            return MessageFormat.format(
                    "{0}.{1}.{2}-{3}",
                    version.getMajorVersion(),
                    version.getMinorVersion(),
                    version.getIncrementalVersion(),
                    versionQualifier
            );
        }

        return MessageFormat.format(
                "{0}.{1}.{2}",
                version.getMajorVersion(),
                version.getMinorVersion(),
                version.getIncrementalVersion()
        );
    }
}
