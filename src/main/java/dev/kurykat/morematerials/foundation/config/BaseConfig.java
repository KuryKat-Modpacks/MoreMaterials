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

package dev.kurykat.morematerials.foundation.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BaseConfig {
    public ForgeConfigSpec specification;

    protected int depth;
    protected List<CValue<?, ?>> allValues;
    protected List<BaseConfig> children;

    public void registerAll(final Builder builder) {
        for (CValue<?, ?> cValue : allValues) {
            cValue.register(builder);
        }
    }

    public void onLoad() {
        if (children != null) {
            children.forEach(BaseConfig::onLoad);
        }
    }

    public void onReload() {
        if (children != null) {
            children.forEach(BaseConfig::onReload);
        }
    }

    public abstract String getName();

    @FunctionalInterface
    protected interface IValueProvider<V, T extends ConfigValue<V>> extends Function<Builder, T> {
    }

    protected BoolConfig boolConfig(boolean defaultValue, String name, String... comment) {
        return new BoolConfig(name, defaultValue, comment);
    }

    protected <T extends Enum<T>> EnumConfig<T> enumConfig(T defaultValue, String name, String... comment) {
        return new EnumConfig<>(name, defaultValue, comment);
    }

    protected FloatConfig floatConfig(float current, float min, float max, String name, String... comment) {
        return new FloatConfig(name, current, min, max, comment);
    }

    protected FloatConfig floatConfig(float current, String name, String... comment) {
        return new FloatConfig(name, current, Float.MIN_VALUE, Float.MAX_VALUE, comment);
    }

    protected IntConfig intConfig(int current, int min, int max, String name, String... comment) {
        return new IntConfig(name, current, min, max, comment);
    }

    protected IntConfig intConfig(int current, String name, String... comment) {
        return new IntConfig(name, current, Integer.MIN_VALUE, Integer.MAX_VALUE, comment);
    }

    protected ConfigGroup configGroup(int depth, String name, String... comment) {
        return new ConfigGroup(name, depth, comment);
    }

    protected <T extends BaseConfig> T nested(int depth, Supplier<T> constructor, String... comment) {
        T config = constructor.get();
        new ConfigGroup(config.getName(), depth, comment);
        new CValue<Boolean, BooleanValue>(config.getName(), builder -> {
            config.depth = depth;
            config.registerAll(builder);
            if (config.depth > depth) {
                builder.pop(config.depth - depth);
            }
            return null;
        });
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(config);
        return config;
    }

    public class CValue<V, T extends ConfigValue<V>> {
        protected ConfigValue<V> value;
        protected String name;

        private final IValueProvider<V, T> provider;

        public CValue(String name, IValueProvider<V, T> provider, String... comment) {
            this.name = name;
            this.provider = builder -> {
                addComments(builder, comment);
                return provider.apply(builder);
            };
            if (allValues == null) {
                allValues = new ArrayList<>();
            }
            allValues.add(this);
        }

        public void addComments(Builder builder, String... comment) {
            if (comment.length > 0) {
                String[] comments = new String[comment.length + 1];
                comments[0] = ".";
                System.arraycopy(comment, 0, comments, 1, comment.length);
                builder.comment(comments);
            } else {
                builder.comment(".");
            }
        }

        public void register(Builder builder) {
            value = provider.apply(builder);
        }

        public V get() {
            return value.get();
        }

        public void set(V value) {
            this.value.set(value);
        }

        public String getName() {
            return name;
        }
    }

    public class ConfigGroup extends CValue<Boolean, BooleanValue> {
        private final int groupDepth;
        private final String[] comment;

        public ConfigGroup(String name, int depth, String... comment) {
            super(name, builder -> null, comment);
            groupDepth = depth;
            this.comment = comment;
        }

        @Override
        public void register(Builder builder) {
            if (depth > groupDepth) {
                builder.pop(depth - groupDepth);
            }
            depth = groupDepth;
            addComments(builder, comment);
            builder.push(getName());
            depth++;
        }
    }

    public class BoolConfig extends CValue<Boolean, BooleanValue> {
        public BoolConfig(String name, boolean defaultValue, String... comment) {
            super(name, builder -> builder.define(name, defaultValue), comment);
        }
    }

    public class EnumConfig<T extends Enum<T>> extends CValue<T, EnumValue<T>> {
        public EnumConfig(String name, T defaultValue, String[] comment) {
            super(name, builder -> builder.defineEnum(name, defaultValue), comment);
        }

    }

    public class FloatConfig extends CValue<Double, DoubleValue> {
        public FloatConfig(String name, float current, float min, float max, String... comment) {
            super(name, builder -> builder.defineInRange(name, current, min, max), comment);
        }

        public float getFloat() {
            return get().floatValue();
        }
    }

    public class IntConfig extends CValue<Integer, IntValue> {
        public IntConfig(String name, int current, int min, int max, String... comment) {
            super(name, builder -> builder.defineInRange(name, current, min, max), comment);
        }
    }
}
