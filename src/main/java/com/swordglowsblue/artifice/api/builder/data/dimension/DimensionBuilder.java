package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class DimensionBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public DimensionBuilder() {
        super(new JsonObject(), JsonResource::new);
    }

    public DimensionBuilder dimensionType(Identifier identifier) {
        this.root.addProperty("type", identifier.toString());
        return this;
    }

    public <T extends ChunkGeneratorTypeBuilder> DimensionBuilder generator(Processor<T> generatorBuilder, T generatorBuilderInstance) {
        with("generator", JsonObject::new, generator -> generatorBuilder.process(generatorBuilderInstance).buildTo(generator));
        return this;
    }

    public DimensionBuilder noiseGenerator(Processor<ChunkGeneratorTypeBuilder.NoiseChunkGeneratorTypeBuilder> generatorBuilder) {
        return this.generator(generatorBuilder, new ChunkGeneratorTypeBuilder.NoiseChunkGeneratorTypeBuilder());
    }

    public DimensionBuilder flatGenerator(Processor<ChunkGeneratorTypeBuilder.FlatChunkGeneratorTypeBuilder> generatorBuilder) {
        return this.generator(generatorBuilder, new ChunkGeneratorTypeBuilder.FlatChunkGeneratorTypeBuilder());
    }

    public DimensionBuilder simpleGenerator(String generatorId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", generatorId);
        this.root.add("generator", jsonObject);
        return this;
    }
}
