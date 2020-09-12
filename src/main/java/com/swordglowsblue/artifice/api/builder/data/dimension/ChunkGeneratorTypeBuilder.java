package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class ChunkGeneratorTypeBuilder extends TypedJsonBuilder<JsonObject> {

    protected ChunkGeneratorTypeBuilder() {
        super(new JsonObject(), j->j);
    }

    /**
     * Set the type (ID) of the Chunk Generator.
     * @param type
     * @param <T>
     * @return
     */
    public <T extends ChunkGeneratorTypeBuilder> T type(String type) {
        this.root.addProperty("type", type);
        return (T) this;
    }

    /**
     * Set the biome Source.
     * @param biomeSourceBuilder
     * @param biomeSourceBuilderInstance
     * @param <T>
     * @return
     */
    public <T extends BiomeSourceBuilder> ChunkGeneratorTypeBuilder biomeSource(Processor<T> biomeSourceBuilder, T biomeSourceBuilderInstance) {
        with("biome_source", JsonObject::new, biomeSource -> biomeSourceBuilder.process(biomeSourceBuilderInstance).buildTo(biomeSource));
        return this;
    }


    public static class NoiseChunkGeneratorTypeBuilder extends ChunkGeneratorTypeBuilder {
        public NoiseChunkGeneratorTypeBuilder() {
            super();
            this.type("minecraft:noise");
        }

        /**
         * Set a seed specially for this dimension.
         * @param seed
         * @return
         */
        public NoiseChunkGeneratorTypeBuilder seed(int seed) {
            this.root.addProperty("seed", seed);
            return this;
        }

        /**
         * @deprecated use noiseSettings instead.
         */
        @Deprecated
        public NoiseChunkGeneratorTypeBuilder presetSettings(String presetId) {
            this.noiseSettings(presetId);
            return this;
        }

        /**
         * Set Noise Settings to Id.
         * @param noiseSettingsID
         * @return
         */
        public NoiseChunkGeneratorTypeBuilder noiseSettings(String noiseSettingsID) {
            this.root.addProperty("settings", noiseSettingsID);
            return this;
        }

        /**
         * Build a vanilla layered biome source.
         * @param biomeSourceBuilder
         * @return
         */
        public NoiseChunkGeneratorTypeBuilder vanillaLayeredBiomeSource(Processor<BiomeSourceBuilder.VanillaLayeredBiomeSourceBuilder> biomeSourceBuilder) {
            biomeSource(biomeSourceBuilder, new BiomeSourceBuilder.VanillaLayeredBiomeSourceBuilder());
            return this;
        }

        /**
         * Build a multi-noise biome source.
         * @param biomeSourceBuilder
         * @return
         */
        public NoiseChunkGeneratorTypeBuilder multiNoiseBiomeSource(Processor<BiomeSourceBuilder.MultiNoiseBiomeSourceBuilder> biomeSourceBuilder) {
            biomeSource(biomeSourceBuilder, new BiomeSourceBuilder.MultiNoiseBiomeSourceBuilder());
            return this;
        }

        /**
         * Build a checkerboard biome source.
         * @param biomeSourceBuilder
         * @return
         */
        public NoiseChunkGeneratorTypeBuilder checkerboardBiomeSource(Processor<BiomeSourceBuilder.CheckerboardBiomeSourceBuilder> biomeSourceBuilder) {
            biomeSource(biomeSourceBuilder, new BiomeSourceBuilder.CheckerboardBiomeSourceBuilder());
            return this;
        }

        /**
         * Build a fixed biome source.
         * @param biomeSourceBuilder
         * @return
         */
        public NoiseChunkGeneratorTypeBuilder fixedBiomeSource(Processor<BiomeSourceBuilder.FixedBiomeSourceBuilder> biomeSourceBuilder) {
            biomeSource(biomeSourceBuilder, new BiomeSourceBuilder.FixedBiomeSourceBuilder());
            return this;
        }

        /**
         * Build a simple biome source.
         * @param id
         * @return
         */
        public NoiseChunkGeneratorTypeBuilder simpleBiomeSource(String id) {
            this.root.addProperty("biome_source", id);
            return this;
        }
    }

    public static class FlatChunkGeneratorTypeBuilder extends ChunkGeneratorTypeBuilder {
        public FlatChunkGeneratorTypeBuilder() {
            super();
            this.type("minecraft:flat");
            this.root.add("settings", new JsonObject());
        }

        /**
         * Build a structure manager.
         * @param structureManagerBuilder
         * @return
         */
        public FlatChunkGeneratorTypeBuilder structureManager(Processor<StructureManagerBuilder> structureManagerBuilder) {
            with(this.root.getAsJsonObject("settings"),"structures", JsonObject::new, jsonObject -> structureManagerBuilder.process(new StructureManagerBuilder()).buildTo(jsonObject));
            return this;
        }

        /**
         * Set the biome to biomeId.
         * @param biomeId
         * @return
         */
        public FlatChunkGeneratorTypeBuilder biome(String biomeId) {
            this.root.getAsJsonObject("settings").addProperty("biome", biomeId);
            return this;
        }

        public FlatChunkGeneratorTypeBuilder lakes(boolean lakes) {
            this.root.getAsJsonObject("settings").addProperty("lakes", lakes);
            return this;
        }

        public FlatChunkGeneratorTypeBuilder features(boolean features) {
            this.root.getAsJsonObject("settings").addProperty("features", features);
            return this;
        }

        /**
         * Add a block layer.
         * @param layersBuilder
         * @return
         */
        public FlatChunkGeneratorTypeBuilder addLayer(Processor<LayersBuilder> layersBuilder) {
            with(this.root.getAsJsonObject("settings"),"layers", JsonArray::new, jsonElements -> jsonElements.add(layersBuilder.process(new LayersBuilder()).buildTo(new JsonObject())));
            return this;
        }

        public static class LayersBuilder extends TypedJsonBuilder<JsonObject> {

            protected LayersBuilder() {
                super(new JsonObject(), j->j);
            }

            /**
             * Set the height of the layer.
             * @param height
             * @return
             */
            public LayersBuilder height(int height) {
                if (height > 255) throw new IllegalArgumentException("Height can't be higher than 255! Found " + height);
                if (height < 0) throw new IllegalArgumentException("Height can't be smaller than 0! Found " + height);
                this.root.addProperty("height", height);
                return this;
            }

            /**
             * Set the block of the layer.
             * @param blockId
             * @return
             */
            public LayersBuilder block(String blockId) {
                this.root.addProperty("block", blockId);
                return this;
            }
        }
    }
}
