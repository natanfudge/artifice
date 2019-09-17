package artificemc.artifice.api.builders.server;

import artificemc.artifice.api.builders.JsonObjectBuilder;
import artificemc.artifice.api.core.util.Processor;
import artificemc.artifice.impl.core.util.IdUtils;

/**
 * Builder for a shaped crafting recipe ({@code namespace:recipes/id.json}).
 * @see <a href="https://minecraft.gamepedia.com/Recipe#JSON_format" target="_blank">Minecraft Wiki</a>
 */
public final class ShapedRecipeBuilder extends RecipeBuilder<ShapedRecipeBuilder> {
    public ShapedRecipeBuilder() { super(); type("crafting_shaped"); }

    /**
     * Set the recipe pattern for this recipe.
     * Each character of the given strings should correspond to a key registered for an ingredient.
     * @param rows The individual rows of the pattern.
     * @return this
     */
    public ShapedRecipeBuilder pattern(String... rows) {
        this.set("pattern", arrayOf(rows));
        return this;
    }

    /**
     * Add an ingredient item.
     * @param key The key in the recipe pattern corresponding to this ingredient.
     * @param id The item ID.
     * @return this
     */
    public ShapedRecipeBuilder ingredientItem(Character key, String id) {
        IdUtils.validateIdentifier(id);
        withObject("key", ingredients ->
            ingredients.set(key.toString(), new JsonObjectBuilder().set("item", id.toString()).build()));
        return this;
    }

    /**
     * Add an ingredient item as any of the given tag.
     * @param key The key in the recipe pattern corresponding to this ingredient.
     * @param id The tag ID.
     * @return this
     */
    public ShapedRecipeBuilder ingredientTag(Character key, String id) {
        IdUtils.validateIdentifier(id);
        withObject("key", ingredients ->
            ingredients.set(key.toString(), new JsonObjectBuilder().set("tag", id.toString()).build()));
        return this;
    }

    /**
     * Add an ingredient item as one of a list of options.
     * @param key The key in the recipe pattern corresponding to this ingredient.
     * @param settings A callback which will be passed a {@link MultiIngredientBuilder}.
     * @return this
     */
    public ShapedRecipeBuilder multiIngredient(Character key, Processor<MultiIngredientBuilder> settings) {
        withObject("key", ingredients ->
            ingredients.set(key.toString(), settings.process(new MultiIngredientBuilder()).build()));
        return this;
    }

    /**
     * Set the item produced by this recipe.
     * @param id The item ID.
     * @param count The number of result items.
     * @return this
     */
    public ShapedRecipeBuilder result(String id, int count) {
        IdUtils.validateIdentifier(id);
        this.set("result", new JsonObjectBuilder().set("item", id.toString()).set("count", count).build());
        return this;
    }
}
