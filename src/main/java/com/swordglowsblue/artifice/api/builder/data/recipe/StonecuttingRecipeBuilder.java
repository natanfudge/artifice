package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.impl.util.Processor;
import net.minecraft.util.Identifier;

public final class StonecuttingRecipeBuilder extends RecipeBuilder<StonecuttingRecipeBuilder> {
    public StonecuttingRecipeBuilder() { super(); type(new Identifier("stonecutting")); }

    public StonecuttingRecipeBuilder ingredientItem(Identifier id) {
        JsonObject ingr = new JsonObject();
        ingr.addProperty("item", id.toString());
        root.add("ingredient", ingr);
        return this;
    }

    public StonecuttingRecipeBuilder ingredientTag(Identifier id) {
        JsonObject ingr = new JsonObject();
        ingr.addProperty("tag", id.toString());
        root.add("ingredient", ingr);
        return this;
    }

    public StonecuttingRecipeBuilder multiIngredient(Processor<MultiIngredientBuilder> settings) {
        root.add("ingredient", settings.process(new MultiIngredientBuilder()).build());
        return this;
    }

    public StonecuttingRecipeBuilder result(Identifier id) {
        root.addProperty("result", id.toString());
        return this;
    }

    public StonecuttingRecipeBuilder count(int count) {
        root.addProperty("count", count);
        return this;
    }
}
