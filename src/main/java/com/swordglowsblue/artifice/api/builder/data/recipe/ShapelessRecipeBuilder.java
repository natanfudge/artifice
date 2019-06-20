package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonArray;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import com.swordglowsblue.artifice.api.util.Processor;
import net.minecraft.util.Identifier;

public final class ShapelessRecipeBuilder extends RecipeBuilder<ShapelessRecipeBuilder> {
    public ShapelessRecipeBuilder() { super(); type(new Identifier("crafting_shapeless")); }

    public ShapelessRecipeBuilder ingredientItem(Identifier id) {
        with("ingredients", JsonArray::new, ingredients ->
            ingredients.add(new JsonObjectBuilder().add("item", id.toString()).build()));
        return this;
    }

    public ShapelessRecipeBuilder ingredientTag(Identifier id) {
        with("ingredients", JsonArray::new, ingredients ->
            ingredients.add(new JsonObjectBuilder().add("tag", id.toString()).build()));
        return this;
    }

    public ShapelessRecipeBuilder multiIngredient(Processor<MultiIngredientBuilder> settings) {
        with("ingredients", JsonArray::new, ingredients ->
            ingredients.add(settings.process(new MultiIngredientBuilder()).build()));
        return this;
    }

    public ShapelessRecipeBuilder result(Identifier id, int count) {
        root.add("result", new JsonObjectBuilder().add("item", id.toString()).add("count", count).build());
        return this;
    }
}
