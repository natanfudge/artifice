package artificemc.artifice.api.builders.server;

import artificemc.artifice.api.builders.TypedJsonBuilder;
import artificemc.artifice.api.core.resource.JsonResource;
import artificemc.artifice.impl.core.util.IdUtils;

/**
 * Base builder for a recipe ({@code namespace:recipes/id.json}).
 * @param <T> this
 * @see <a href="https://minecraft.gamepedia.com/Recipe#JSON_format" target="_blank">Minecraft Wiki</a>
 */
@SuppressWarnings("unchecked")
public abstract class RecipeBuilder<T extends RecipeBuilder<T>> extends TypedJsonBuilder<JsonResource, T> {
    protected RecipeBuilder() { super(JsonResource::new); }

    /**
     * Set the type of this recipe.
     * @param id The type ID.
     * @return this
     */
    public T type(String id) {
        IdUtils.validateIdentifier(id);
        this.set("type", id);
        return (T)this;
    }

    /**
     * Set the recipe book group of this recipe.
     * @param id The group ID.
     * @return this
     */
    public T group(String id) {
        IdUtils.validateIdentifier(id);
        this.set("group", id);
        return (T)this;
    }
}
