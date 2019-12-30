package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import net.minecraft.util.Identifier;

/**
 * Base builder for a recipe ({@code namespace:recipes/id.json}).
 * @param <T> this
 * @see <a href="https://minecraft.gamepedia.com/Recipe#JSON_format" target="_blank">Minecraft Wiki</a>
 */
@SuppressWarnings("unchecked")
public abstract class RecipeBuilder<T extends RecipeBuilder<T>> extends TypedJsonBuilder<JsonResource<JsonObject>> {
    protected RecipeBuilder() { super(new JsonObject(), JsonResource::new); }

    /**
     * Set the type of this recipe.
     * @param id The type ID.
     * @return this
     */
    public T type(Identifier id) {
        root.addProperty("type", id.toString());
        return (T)this;
    }

    /**
     * Set the recipe book group of this recipe.
     * @param id The group ID.
     * @return this
     */
    public T group(Identifier id) {
        root.addProperty("group", id.toString());
        return (T)this;
    }
}
