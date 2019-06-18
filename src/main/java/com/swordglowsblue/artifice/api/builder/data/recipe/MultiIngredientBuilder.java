package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

public final class MultiIngredientBuilder {
    private final JsonArray ingredients = new JsonArray();
    MultiIngredientBuilder() {}

    public MultiIngredientBuilder item(Identifier id) {
        JsonObject ingr = new JsonObject();
        ingr.addProperty("item", id.toString());
        ingredients.add(ingr);
        return this;
    }

    public MultiIngredientBuilder tag(Identifier id) {
        JsonObject ingr = new JsonObject();
        ingr.addProperty("tag", id.toString());
        ingredients.add(ingr);
        return this;
    }

    JsonArray build() { return ingredients; }
}
