package artificemc.artifice.api.builders.server;

import artificemc.artifice.api.builders.JsonObjectBuilder;
import artificemc.artifice.impl.core.util.IdUtils;
import com.google.gson.JsonArray;

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
    public MultiIngredientBuilder item(String id) {
        IdUtils.validateIdentifier(id);
        ingredients.add(new JsonObjectBuilder().set("item", id.toString()).build());
        return this;
    }

    /**
     * Add all items from the given tag as options.
     * @param id The tag ID.
     * @return this
     */
    public MultiIngredientBuilder tag(String id) {
        IdUtils.validateIdentifier(id);
        ingredients.add(new JsonObjectBuilder().set("tag", id.toString()).build());
        return this;
    }

    JsonArray build() { return ingredients; }
}
