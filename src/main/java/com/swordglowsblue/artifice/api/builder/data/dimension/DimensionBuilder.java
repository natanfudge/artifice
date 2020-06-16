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

    /**
     * Set the dimension type.
     * @param identifier
     * @return
     */
    public DimensionBuilder dimensionType(Identifier identifier) {
        this.root.addProperty("type", identifier.toString());
        return this;
    }

    /**
     * Make a Chunk Generator.
     * @param generatorBuilder
     * @param generatorBuilderInstance
     * @param <T> A class extending ChunkGeneratorTypeBuilder.
     * @return
     */
    public <T extends ChunkGeneratorTypeBuilder> DimensionBuilder generator(Processor<T> generatorBuilder, T generatorBuilderInstance) {
        with("generator", JsonObject::new, generator -> generatorBuilder.process(generatorBuilderInstance).buildTo(generator));
        return this;
    }

    /**
     * Make a noise based Chunk Generator.
     * @param generatorBuilder
     * @return
     */
    public DimensionBuilder noiseGenerator(Processor<ChunkGeneratorTypeBuilder.NoiseChunkGeneratorTypeBuilder> generatorBuilder) {
        return this.generator(generatorBuilder, new ChunkGeneratorTypeBuilder.NoiseChunkGeneratorTypeBuilder());
    }

    /**
     * Make a flat Chunk Generator.
     * @param generatorBuilder
     * @return
     */
    public DimensionBuilder flatGenerator(Processor<ChunkGeneratorTypeBuilder.FlatChunkGeneratorTypeBuilder> generatorBuilder) {
        return this.generator(generatorBuilder, new ChunkGeneratorTypeBuilder.FlatChunkGeneratorTypeBuilder());
    }

    /**
     * Use with a Chunk Generator which doesn't need any configuration. Example: Debug Mode Generator.
     * @param generatorId The ID of the chunk generator type.
     * @return this
     */
    public DimensionBuilder simpleGenerator(String generatorId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", generatorId);
        this.root.add("generator", jsonObject);
        return this;
    }
}
