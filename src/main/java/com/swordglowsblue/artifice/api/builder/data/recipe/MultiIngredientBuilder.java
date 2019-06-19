package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import net.minecraft.util.Identifier;

public final class MultiIngredientBuilder {
    private final JsonArray ingredients = new JsonArray();
    MultiIngredientBuilder() {}

    public MultiIngredientBuilder item(Identifier id) {
        ingredients.add(new JsonObjectBuilder().add("item", id.toString()).build());
        return this;
    }

    public MultiIngredientBuilder tag(Identifier id) {
        ingredients.add(new JsonObjectBuilder().add("tag", id.toString()).build());
        return this;
    }

    JsonArray build() { return ingredients; }
}
