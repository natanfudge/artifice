package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.builder.data.AdvancementBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class BiomeSourceBuilder extends TypedJsonBuilder<JsonObject> {

    public BiomeSourceBuilder() {
        super(new JsonObject(), j->j);
    }

    public <T extends BiomeSourceBuilder> T seed(int seed) {
        this.root.addProperty("seed", seed);
        return (T)this;
    }

    public <T extends BiomeSourceBuilder> T type(String type) {
        this.root.addProperty("type", type);
        return (T) this;
    }


    public static class VanillaLayeredBiomeSourceBuilder extends BiomeSourceBuilder {
        public VanillaLayeredBiomeSourceBuilder() {
            super();
            this.type("minecraft:vanilla_layered");
        }

        public VanillaLayeredBiomeSourceBuilder largeBiomes(boolean largeBiomes) {
            this.root.addProperty("large_biomes", largeBiomes);
            return this;
        }

        public VanillaLayeredBiomeSourceBuilder legacyBiomeInitLayer(boolean legacyBiomeInitLayer) {
            this.root.addProperty("legacy_biome_init_layer", legacyBiomeInitLayer);
            return this;
        }
    }

    public static class MultiNoiseBiomeSourceBuilder extends BiomeSourceBuilder {
        public MultiNoiseBiomeSourceBuilder() {
            super();
            this.type("minecraft:multi_noise");
        }

        public MultiNoiseBiomeSourceBuilder preset(String preset) {
            this.root.addProperty("preset", preset);
            return this;
        }

        public MultiNoiseBiomeSourceBuilder biomes(Processor<BiomeBuilder> biomeBuilder) {
            with("biomes", JsonArray::new, biomeArray -> biomeArray.add(biomeBuilder.process(new BiomeBuilder()).buildTo(new JsonObject())));
            return this;
        }

        public static class BiomeBuilder extends TypedJsonBuilder<JsonObject> {
            protected BiomeBuilder() {
                super(new JsonObject(), j->j);
            }

            public BiomeBuilder biome(String id) {
                this.root.addProperty("biome", id);
                return this;
            }

            public BiomeBuilder parameters(Processor<BiomeParametersBuilder> biomeSettingsBuilder) {
                with("parameters", JsonObject::new, biomeSettings -> biomeSettingsBuilder.process(new BiomeParametersBuilder()).buildTo(biomeSettings));
                return this;
            }
        }

        public static class BiomeParametersBuilder extends TypedJsonBuilder<JsonObject> {
            protected BiomeParametersBuilder() {
                super(new JsonObject(), j->j);
            }

            public BiomeParametersBuilder altitude(float altitude) {
                this.root.addProperty("altitude", altitude);
                return this;
            }

            public BiomeParametersBuilder weirdness(float weirdness) {
                this.root.addProperty("weirdness", weirdness);
                return this;
            }

            public BiomeParametersBuilder offset(float offset) {
                this.root.addProperty("offset", offset);
                return this;
            }

            public BiomeParametersBuilder temperature(float temperature) {
                this.root.addProperty("temperature", temperature);
                return this;
            }

            public BiomeParametersBuilder humidity(float humidity) {
                this.root.addProperty("humidity", humidity);
                return this;
            }
        }
    }

    public static class CheckerboardBiomeSourceBuilder extends BiomeSourceBuilder {
        private JsonArray jsonArray = new JsonArray();

        public CheckerboardBiomeSourceBuilder() {
            super();
            this.type("minecraft:checkerboard");
        }

        public CheckerboardBiomeSourceBuilder scale(int scale) {
            this.root.addProperty("scale", scale);
            return this;
        }

        public CheckerboardBiomeSourceBuilder addBiome(String biomeId) {
            this.jsonArray.add(biomeId);
            return this;
        }

        @Override
        public JsonObject buildTo(JsonObject target) {
            this.root.add("biomes", this.jsonArray);
            return super.buildTo(target);
        }
    }

    public static class FixedBiomeSourceBuilder extends BiomeSourceBuilder {
        public FixedBiomeSourceBuilder() {
            super();
            this.type("minecraft:fixed");
        }

        public FixedBiomeSourceBuilder biome(String biomeId) {
            this.root.addProperty("biome", biomeId);
            return this;
        }
    }
}
