package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;

import java.util.function.Function;

public final class ChunkGeneratorTypeBuilder extends TypedJsonBuilder<JsonObject> {
    public ChunkGeneratorTypeBuilder() {
        super(new JsonObject(), j->j);
    }
}
