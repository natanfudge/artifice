package com.swordglowsblue.artifice.api.builder.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import net.minecraft.util.Identifier;

public final class TagBuilder extends TypedJsonBuilder<JsonResource> {
    public TagBuilder() { super(new JsonObject(), JsonResource::new); }

    public TagBuilder replace(boolean replace) {
        root.addProperty("replace", replace);
        return this;
    }

    public TagBuilder value(Identifier id) {
        with("values", JsonArray::new, values -> values.add(id.toString()));
        return this;
    }

    public TagBuilder values(Identifier... ids) {
        with("values", JsonArray::new, values -> {
            for(Identifier id : ids) values.add(id.toString());
        });
        return this;
    }
}
