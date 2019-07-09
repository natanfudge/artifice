package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonElement;
import com.swordglowsblue.artifice.api.builder.JsonArrayBuilder;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

/**
 * Builder for a recipe of an unknown type ({@code namespace:recipes/id.json})
 * @see <a href="https://minecraft.gamepedia.com/Recipe#JSON_format" target="_blank">Minecraft Wiki</a>
 */
public final class GenericRecipeBuilder extends RecipeBuilder<GenericRecipeBuilder> {
    /**
     * Add a JSON element to this recipe.
     * @param name The key.
     * @param value The value.
     * @return this
     */
    public RecipeBuilder add(String name, JsonElement value) {
        root.add(name, value);
        return this;
    }

    /**
     * Add a string to this recipe.
     * @param name The key.
     * @param value The value.
     * @return this
     */
    public RecipeBuilder add(String name, String value) {
        root.addProperty(name, value);
        return this;
    }

    /**
     * Add a boolean to this recipe.
     * @param name The key.
     * @param value The value.
     * @return this
     */
    public RecipeBuilder add(String name, boolean value) {
        root.addProperty(name, value);
        return this;
    }

    /**
     * Add a number to this recipe.
     * @param name The key.
     * @param value The value.
     * @return this
     */
    public RecipeBuilder add(String name, Number value) {
        root.addProperty(name, value);
        return this;
    }

    /**
     * Add a character to this recipe.
     * @param name The key.
     * @param value The value.
     * @return this
     */
    public RecipeBuilder add(String name, Character value) {
        root.addProperty(name, value);
        return this;
    }

    /**
     * Add a JSON object to this recipe.
     * @param name The key.
     * @param settings A callback which will be passed a {@link JsonObjectBuilder}.
     * @return this
     */
    public RecipeBuilder addObject(String name, Processor<JsonObjectBuilder> settings) {
        root.add(name, settings.process(new JsonObjectBuilder()).build());
        return this;
    }

    /**
     * Add a JSON array to this recipe.
     * @param name The key.
     * @param settings A callback which will be passed a {@link JsonArrayBuilder}.
     * @return this
     */
    public RecipeBuilder addArray(String name, Processor<JsonArrayBuilder> settings) {
        root.add(name, settings.process(new JsonArrayBuilder()).build());
        return this;
    }
}
