package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

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
}
