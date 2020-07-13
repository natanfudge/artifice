package com.swordglowsblue.artifice.api.builder.data.worldgen;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;

public class class_5428Builder extends TypedJsonBuilder<JsonObject> {
    public class_5428Builder() {
        super(new JsonObject(), j->j);
    }

    public class_5428Builder base(int base) {
        this.root.addProperty("base", base);
        return this;
    }

    public class_5428Builder spread(int spread) {
        this.root.addProperty("spread", spread);
        return this;
    }
}
