package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.util.Processor;
import net.minecraft.util.Identifier;

public final class CookingRecipeBuilder extends RecipeBuilder<CookingRecipeBuilder> {
    public CookingRecipeBuilder ingredientItem(Identifier id) {
        JsonObject ingr = new JsonObject();
        ingr.addProperty("item", id.toString());
        root.add("ingredient", ingr);
        return this;
    }

    public CookingRecipeBuilder ingredientTag(Identifier id) {
        JsonObject ingr = new JsonObject();
        ingr.addProperty("tag", id.toString());
        root.add("ingredient", ingr);
        return this;
    }

    public CookingRecipeBuilder multiIngredient(Processor<MultiIngredientBuilder> settings) {
        root.add("ingredient", settings.process(new MultiIngredientBuilder()).build());
        return this;
    }

    public CookingRecipeBuilder result(Identifier id) {
        root.addProperty("result", id.toString());
        return this;
    }

    public CookingRecipeBuilder experience(double exp) {
        root.addProperty("experience", exp);
        return this;
    }

    public CookingRecipeBuilder cookingTime(int time) {
        root.addProperty("cookingtime", time);
        return this;
    }
}
