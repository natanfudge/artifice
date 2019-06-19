package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import com.swordglowsblue.artifice.api.util.Processor;
import net.minecraft.util.Identifier;

public final class ShapedRecipeBuilder extends RecipeBuilder<ShapedRecipeBuilder> {
    public ShapedRecipeBuilder() { super(); type(new Identifier("crafting_shaped")); }

    public ShapedRecipeBuilder pattern(String... rows) {
        root.add("pattern", arrayOf(rows));
        return this;
    }

    public ShapedRecipeBuilder ingredientItem(Character key, Identifier id) {
        with("key", JsonObject::new, ingredients ->
            ingredients.add(key.toString(), new JsonObjectBuilder().add("item", id.toString()).build()));
        return this;
    }

    public ShapedRecipeBuilder ingredientTag(Character key, Identifier id) {
        with("key", JsonObject::new, ingredients ->
            ingredients.add(key.toString(), new JsonObjectBuilder().add("tag", id.toString()).build()));
        return this;
    }

    public ShapedRecipeBuilder multiIngredient(Character key, Processor<MultiIngredientBuilder> settings) {
        with("key", JsonObject::new, ingredients ->
            ingredients.add(key.toString(), settings.process(new MultiIngredientBuilder()).build()));
        return this;
    }

    public ShapedRecipeBuilder result(Identifier id, int count) {
        root.add("result", new JsonObjectBuilder().add("item", id.toString()).add("count", count).build());
        return this;
    }
}
