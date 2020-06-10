package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;
import sun.tools.java.Identifier;

public final class DimensionBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public DimensionBuilder() {
        super(new JsonObject(), JsonResource::new);
    }

    public DimensionBuilder dimensionType(Identifier identifier) {
        this.root.addProperty("type", identifier.toString());
        return this;
    }

    public DimensionBuilder generator(Identifier identifier, Processor<ChunkGeneratorTypeBuilder> generatorBuilder) {
        with("generator", JsonObject::new, generator -> with(generator, identifier.toString(), JsonObject::new, gen -> generatorBuilder.process(new ChunkGeneratorTypeBuilder()).buildTo(gen)));
        return this;
    }
}
