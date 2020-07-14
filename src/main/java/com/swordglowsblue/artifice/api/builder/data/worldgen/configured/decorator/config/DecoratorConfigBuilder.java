package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;

public class DecoratorConfigBuilder extends TypedJsonBuilder<JsonObject> {

    public DecoratorConfigBuilder() {
        super(new JsonObject(), j->j);
    }
}
