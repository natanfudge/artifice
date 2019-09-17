package artificemc.artifice.api.builders.server;

import artificemc.artifice.api.builders.JsonArrayBuilder;
import artificemc.artifice.api.builders.JsonObjectBuilder;
import artificemc.artifice.api.core.util.Processor;
import com.google.gson.JsonElement;

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
        this.set(name, value);
        return this;
    }

    /**
     * Add a string to this recipe.
     * @param name The key.
     * @param value The value.
     * @return this
     */
    public RecipeBuilder add(String name, String value) {
        this.set(name, value);
        return this;
    }

    /**
     * Add a boolean to this recipe.
     * @param name The key.
     * @param value The value.
     * @return this
     */
    public RecipeBuilder add(String name, boolean value) {
        this.set(name, value);
        return this;
    }

    /**
     * Add a number to this recipe.
     * @param name The key.
     * @param value The value.
     * @return this
     */
    public RecipeBuilder add(String name, Number value) {
        this.set(name, value);
        return this;
    }

    /**
     * Add a character to this recipe.
     * @param name The key.
     * @param value The value.
     * @return this
     */
    public RecipeBuilder add(String name, Character value) {
        this.set(name, value);
        return this;
    }

    /**
     * Add a JSON object to this recipe.
     * @param name The key.
     * @param settings A callback which will be passed a {@link JsonObjectBuilder}.
     * @return this
     */
    public RecipeBuilder addObject(String name, Processor<JsonObjectBuilder> settings) {
        this.set(name, settings.process(new JsonObjectBuilder()).build());
        return this;
    }

    /**
     * Add a JSON array to this recipe.
     * @param name The key.
     * @param settings A callback which will be passed a {@link JsonArrayBuilder}.
     * @return this
     */
    public RecipeBuilder addArray(String name, Processor<JsonArrayBuilder> settings) {
        this.set(name, settings.process(new JsonArrayBuilder()).build());
        return this;
    }
}
