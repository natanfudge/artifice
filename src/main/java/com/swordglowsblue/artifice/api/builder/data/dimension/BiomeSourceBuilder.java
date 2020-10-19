package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class BiomeSourceBuilder extends TypedJsonBuilder<JsonObject> {

    public BiomeSourceBuilder() {
        super(new JsonObject(), j->j);
    }

    /**
     * Set the seed of the biome source.
     * @param seed
     * @param <T>
     * @return
     */
    public <T extends BiomeSourceBuilder> T seed(int seed) {
        this.root.addProperty("seed", seed);
        return (T)this;
    }

    /**
     * Set the type of the biome source.
     * @param type
     * @param <T>
     * @return
     */
    public <T extends BiomeSourceBuilder> T type(String type) {
        this.root.addProperty("type", type);
        return (T) this;
    }


    public static class VanillaLayeredBiomeSourceBuilder extends BiomeSourceBuilder {
        public VanillaLayeredBiomeSourceBuilder() {
            super();
            this.type("minecraft:vanilla_layered");
        }

        /**
         * @param largeBiomes
         * @return
         */
        public VanillaLayeredBiomeSourceBuilder largeBiomes(boolean largeBiomes) {
            this.root.addProperty("large_biomes", largeBiomes);
            return this;
        }

        /**
         * @param legacyBiomeInitLayer
         * @return
         */
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

        /**
         * Set the preset.
         * @param preset
         * @return
         */
        public MultiNoiseBiomeSourceBuilder preset(String preset) {
            this.root.addProperty("preset", preset);
            return this;
        }

        /**
         * Add biome.
         * @param biomeBuilder
         * @return
         */
        public MultiNoiseBiomeSourceBuilder addBiome(Processor<BiomeBuilder> biomeBuilder) {
            with("biomes", JsonArray::new, biomeArray -> biomeArray.add(biomeBuilder.process(new BiomeBuilder()).buildTo(new JsonObject())));
            return this;
        }

        public static class BiomeBuilder extends TypedJsonBuilder<JsonObject> {
            protected BiomeBuilder() {
                super(new JsonObject(), j->j);
            }

            /**
             * Set the biome ID.
             * @param id
             * @return
             */
            public BiomeBuilder biome(String id) {
                this.root.addProperty("biome", id);
                return this;
            }

            /**
             * Build biome parameters.
             * @param biomeSettingsBuilder
             * @return
             */
            public BiomeBuilder parameters(Processor<BiomeParametersBuilder> biomeSettingsBuilder) {
                with("parameters", JsonObject::new, biomeSettings -> biomeSettingsBuilder.process(new BiomeParametersBuilder()).buildTo(biomeSettings));
                return this;
            }
        }

        public static class BiomeParametersBuilder extends TypedJsonBuilder<JsonObject> {
            protected BiomeParametersBuilder() {
                super(new JsonObject(), j->j);
            }

            /**
             * @param altitude
             * @return
             */
            public BiomeParametersBuilder altitude(float altitude) {
                if (altitude > 2.0F) throw new IllegalArgumentException("altitude can't be higher than 2.0F! Found " + altitude);
                if (altitude < -2.0F) throw new IllegalArgumentException("altitude can't be smaller than 2.0F! Found " + altitude);
                this.root.addProperty("altitude", altitude);
                return this;
            }

            /**
             * @param weirdness
             * @return
             */
            public BiomeParametersBuilder weirdness(float weirdness) {
                if (weirdness > 2.0F) throw new IllegalArgumentException("weirdness can't be higher than 2.0F! Found " + weirdness);
                if (weirdness < -2.0F) throw new IllegalArgumentException("weirdness can't be smaller than 2.0F! Found " + weirdness);
                this.root.addProperty("weirdness", weirdness);
                return this;
            }

            /**
             * @param offset
             * @return
             */
            public BiomeParametersBuilder offset(float offset) {
                if (offset > 1.0F) throw new IllegalArgumentException("offset can't be higher than 1.0F! Found " + offset);
                if (offset < 0.0F) throw new IllegalArgumentException("offset can't be smaller than 0.0F! Found " + offset);
                this.root.addProperty("offset", offset);
                return this;
            }

            /**
             * @param temperature
             * @return
             */
            public BiomeParametersBuilder temperature(float temperature) {
                if (temperature > 2.0F) throw new IllegalArgumentException("temperature can't be higher than 2.0F! Found " + temperature);
                if (temperature < -2.0F) throw new IllegalArgumentException("temperature can't be smaller than 2.0F! Found " + temperature);
                this.root.addProperty("temperature", temperature);
                return this;
            }

            /**
             * @param humidity
             * @return
             */
            public BiomeParametersBuilder humidity(float humidity) {
                if (humidity > 2.0F) throw new IllegalArgumentException("humidity can't be higher than 2.0F! Found " + humidity);
                if (humidity < -2.0F) throw new IllegalArgumentException("humidity can't be smaller than 2.0F! Found " + humidity);
                this.root.addProperty("humidity", humidity);
                return this;
            }
        }

        /**
         * @param noiseSettings
         * @return this
         */
        public MultiNoiseBiomeSourceBuilder altitudeNoise(Processor<NoiseSettings> noiseSettings) {
            with("altitude_noise", JsonObject::new, jsonObject -> noiseSettings.process(new NoiseSettings()).buildTo(jsonObject));
            return this;
        }

        /**
         * @param noiseSettings
         * @return this
         */
        public MultiNoiseBiomeSourceBuilder weirdnessNoise(Processor<NoiseSettings> noiseSettings) {
            with("weirdness_noise", JsonObject::new, jsonObject -> noiseSettings.process(new NoiseSettings()).buildTo(jsonObject));
            return this;
        }

        /**
         * @param noiseSettings
         * @return this
         */
        public MultiNoiseBiomeSourceBuilder temperatureNoise(Processor<NoiseSettings> noiseSettings) {
            with("temperature_noise", JsonObject::new, jsonObject -> noiseSettings.process(new NoiseSettings()).buildTo(jsonObject));
            return this;
        }

        /**
         * @param noiseSettings
         * @return this
         */
        public MultiNoiseBiomeSourceBuilder humidityNoise(Processor<NoiseSettings> noiseSettings) {
            with("humidity_noise", JsonObject::new, jsonObject -> noiseSettings.process(new NoiseSettings()).buildTo(jsonObject));
            return this;
        }

        public static class NoiseSettings extends TypedJsonBuilder<JsonObject> {
            protected NoiseSettings() {
                super(new JsonObject(), j->j);
            }

            /**
             * Changes how much detail the noise of the respective value has
             * @param octave how much detail the noise of the respective value has
             * @return this
             */
            public NoiseSettings firstOctave(int octave) {
                this.root.addProperty("firstOctave", octave);
                return this;
            }

            /**
             * @param amplitudes the amplitudes you want
             * @return this
             */
            public NoiseSettings amplitudes(float... amplitudes) {
                jsonArray("amplitudes", jsonArrayBuilder -> {
                    for (float amplitude : amplitudes) {
                        jsonArrayBuilder.add(amplitude);
                    }
                });
                return this;
            }
        }

        public static class AmplitudesBuilder extends TypedJsonBuilder<JsonObject> {
            protected AmplitudesBuilder() {
                super(new JsonObject(), j->j);
            }

            /**
             * @param amplitude idk
             * @return
             */
            public AmplitudesBuilder amplitude(float amplitude) {
                this.root.addProperty("altitude", amplitude);
                return this;
            }
        }

    }

    public static class CheckerboardBiomeSourceBuilder extends BiomeSourceBuilder {

        public CheckerboardBiomeSourceBuilder() {
            super();
            this.type("minecraft:checkerboard");
            this.root.add("biomes", new JsonArray());
        }

        /**
         * @param scale
         * @return
         */
        public CheckerboardBiomeSourceBuilder scale(int scale) {
            if (scale > 62) throw new IllegalArgumentException("Scale can't be higher than 62! Found " + scale);
            if (scale < 0) throw new IllegalArgumentException("Scale can't be smaller than 0! Found " + scale);
            this.root.addProperty("scale", scale);
            return this;
        }

        /**
         * Add biome.
         * @param biomeId
         * @return
         */
        public CheckerboardBiomeSourceBuilder addBiome(String biomeId) {
            this.root.getAsJsonArray("biomes").add(biomeId);
            return this;
        }
    }

    public static class FixedBiomeSourceBuilder extends BiomeSourceBuilder {
        public FixedBiomeSourceBuilder() {
            super();
            this.type("minecraft:fixed");
        }

        /**
         * Set biome ID.
         * @param biomeId
         * @return
         */
        public FixedBiomeSourceBuilder biome(String biomeId) {
            this.root.addProperty("biome", biomeId);
            return this;
        }
    }
}
