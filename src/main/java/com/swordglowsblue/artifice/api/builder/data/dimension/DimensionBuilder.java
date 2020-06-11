package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;
import net.minecraft.util.Identifier;

public final class DimensionBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public DimensionBuilder() {
        super(new JsonObject(), JsonResource::new);
    }

    public DimensionBuilder dimensionType(Identifier identifier) {
        this.root.addProperty("type", identifier.toString());
        return this;
    }

    public DimensionBuilder generator(Processor<ChunkGeneratorTypeBuilder> generatorBuilder) {
        with("generator", JsonObject::new, generator -> generatorBuilder.process(new ChunkGeneratorTypeBuilder()).buildTo(generator));
        return this;
    }
}
