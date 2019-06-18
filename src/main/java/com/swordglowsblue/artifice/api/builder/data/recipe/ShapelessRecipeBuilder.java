package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.impl.util.Processor;
import net.minecraft.util.Identifier;

public final class ShapelessRecipeBuilder extends RecipeBuilder<ShapelessRecipeBuilder> {
    public ShapelessRecipeBuilder() { super(); type(new Identifier("crafting_shapeless")); }

    public ShapelessRecipeBuilder ingredientItem(Identifier id) {
        with("ingredients", JsonArray::new, ingredients -> {
            JsonObject ingr = new JsonObject();
            ingr.addProperty("item", id.toString());
            ingredients.add(ingr);
        });
        return this;
    }

    public ShapelessRecipeBuilder ingredientTag(Identifier id) {
        with("ingredients", JsonArray::new, ingredients -> {
            JsonObject ingr = new JsonObject();
            ingr.addProperty("tag", id.toString());
            ingredients.add(ingr);
        });
        return this;
    }

    public ShapelessRecipeBuilder multiIngredient(Processor<MultiIngredientBuilder> settings) {
        with("ingredients", JsonArray::new, ingredients -> {
            ingredients.add(settings.process(new MultiIngredientBuilder()).build());
        });
        return this;
    }

    public ShapelessRecipeBuilder result(Identifier id, int count) {
        JsonObject result = new JsonObject();
        result.addProperty("item", id.toString());
        result.addProperty("count", count);
        root.add("result", result);
        return this;
    }
}
