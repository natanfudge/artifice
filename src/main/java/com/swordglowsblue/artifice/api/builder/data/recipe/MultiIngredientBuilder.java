package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonArray;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import net.minecraft.util.Identifier;

/**
 * Bulder for a recipe ingredient option list.
 * @see CookingRecipeBuilder
 * @see ShapedRecipeBuilder
 * @see ShapelessRecipeBuilder
 * @see StonecuttingRecipeBuilder
 */
public final class MultiIngredientBuilder {
    private final JsonArray ingredients = new JsonArray();
    MultiIngredientBuilder() {}

    /**
     * Add an item as an option.
     * @param id The item ID.
     * @return this
     */
    public MultiIngredientBuilder item(Identifier id) {
        ingredients.add(new JsonObjectBuilder().add("item", id.toString()).build());
        return this;
    }

    /**
     * Add all items from the given tag as options.
     * @param id The tag ID.
     * @return this
     */
    public MultiIngredientBuilder tag(Identifier id) {
        ingredients.add(new JsonObjectBuilder().add("tag", id.toString()).build());
        return this;
    }

    JsonArray build() { return ingredients; }
}
