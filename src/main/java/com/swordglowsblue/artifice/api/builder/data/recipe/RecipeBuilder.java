package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import net.minecraft.util.Identifier;

public abstract class RecipeBuilder<T extends RecipeBuilder<T>> extends TypedJsonBuilder<JsonResource> {
    public RecipeBuilder() { super(new JsonObject(), JsonResource::new); }

    public T type(Identifier id) {
        root.addProperty("type", id.toString());
        return (T)this;
    }

    public T group(Identifier id) {
        root.addProperty("group", id.toString());
        return (T)this;
    }
}
