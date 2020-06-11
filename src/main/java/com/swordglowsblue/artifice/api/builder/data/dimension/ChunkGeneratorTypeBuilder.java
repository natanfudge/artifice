package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

import java.util.function.Function;

public class ChunkGeneratorTypeBuilder extends TypedJsonBuilder<JsonObject> {

    protected ChunkGeneratorTypeBuilder() {
        super(new JsonObject(), j->j);
    }

    public <T extends ChunkGeneratorTypeBuilder> T type(String type) {
        this.root.addProperty("type", type);
        return (T) this;
    }


    public static class NoiseChunkGeneratorTypeBuilder extends ChunkGeneratorTypeBuilder {
        public NoiseChunkGeneratorTypeBuilder() {
            super();
            this.type("minecraft:noise");
        }

        public NoiseChunkGeneratorTypeBuilder seed(int seed) {
            this.root.addProperty("seed", seed);
            return this;
        }

        public NoiseChunkGeneratorTypeBuilder presetSettings(String presetId) {
            this.root.addProperty("settings", presetId);
            return this;
        }

        public NoiseChunkGeneratorTypeBuilder customSettings(Processor<GeneratorSettingsBuilder> generatorSettingsBuilder) {
            with("settings", JsonObject::new, generatorSettings -> generatorSettingsBuilder.process(new GeneratorSettingsBuilder()).buildTo(generatorSettings));
            return this;
        }

        public <T extends BiomeSourceBuilder> NoiseChunkGeneratorTypeBuilder biomeSource(Processor<T> biomeSourceBuilder, T biomeSourceBuilderInstance) {
            with("biome_source", JsonObject::new, biomeSource -> biomeSourceBuilder.process(biomeSourceBuilderInstance).buildTo(biomeSource));
            return this;
        }

        public NoiseChunkGeneratorTypeBuilder vanillaLayeredBiomeSource(Processor<BiomeSourceBuilder.VanillaLayeredBiomeSourceBuilder> biomeSourceBuilder) {
            biomeSource(biomeSourceBuilder, new BiomeSourceBuilder.VanillaLayeredBiomeSourceBuilder());
            return this;
        }

        public NoiseChunkGeneratorTypeBuilder multiNoiseBiomeSource(Processor<BiomeSourceBuilder.MultiNoiseBiomeSourceBuilder> biomeSourceBuilder) {
            biomeSource(biomeSourceBuilder, new BiomeSourceBuilder.MultiNoiseBiomeSourceBuilder());
            return this;
        }

        public NoiseChunkGeneratorTypeBuilder checkerboardBiomeSource(Processor<BiomeSourceBuilder.CheckerboardBiomeSourceBuilder> biomeSourceBuilder) {
            biomeSource(biomeSourceBuilder, new BiomeSourceBuilder.CheckerboardBiomeSourceBuilder());
            return this;
        }

        public NoiseChunkGeneratorTypeBuilder fixedBiomeSource(Processor<BiomeSourceBuilder.FixedBiomeSourceBuilder> biomeSourceBuilder) {
            biomeSource(biomeSourceBuilder, new BiomeSourceBuilder.FixedBiomeSourceBuilder());
            return this;
        }

        public NoiseChunkGeneratorTypeBuilder simpleBiomeSource(String id) {
            this.root.addProperty("biome_source", id);
            return this;
        }
    }

    public static class FlatChunkGeneratorTypeBuilder extends ChunkGeneratorTypeBuilder {
        public FlatChunkGeneratorTypeBuilder() {
            super();
            this.type("minecraft:flat");
        }

        public FlatChunkGeneratorTypeBuilder structureManager(Processor<StructureManagerBuilder> structureManagerBuilder) {
            with("structures", JsonObject::new, jsonObject -> structureManagerBuilder.process(new StructureManagerBuilder()).buildTo(jsonObject));
            return this;
        }

        public FlatChunkGeneratorTypeBuilder biome(String biomeId) {
            this.root.addProperty("biome", biomeId);
            return this;
        }

        public FlatChunkGeneratorTypeBuilder addLayer(Processor<LayersBuilder> layersBuilder) {
            with("layers", JsonArray::new, jsonElements -> layersBuilder.process(new LayersBuilder()).buildTo(jsonElements.getAsJsonObject()));
            return this;
        }

        public static class LayersBuilder extends TypedJsonBuilder<JsonObject> {

            protected LayersBuilder() {
                super(new JsonObject(), j->j);
            }

            public LayersBuilder height(int height) {
                this.root.addProperty("height", height);
                return this;
            }

            public LayersBuilder block(String blockId) {
                this.root.addProperty("block", blockId);
                return this;
            }
        }
    }
}
