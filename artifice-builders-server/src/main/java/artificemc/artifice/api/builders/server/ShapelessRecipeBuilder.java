package artificemc.artifice.api.builders.server;

import artificemc.artifice.api.builders.JsonObjectBuilder;
import artificemc.artifice.api.core.util.Processor;
import artificemc.artifice.impl.core.util.IdUtils;

/**
 * Builder for a shapeless crafting recipe ({@code namespace:recipes/id.json}).
 * @see <a href="https://minecraft.gamepedia.com/Recipe#JSON_format" target="_blank">Minecraft Wiki</a>
 */
public final class ShapelessRecipeBuilder extends RecipeBuilder<ShapelessRecipeBuilder> {
    public ShapelessRecipeBuilder() { super(); type("crafting_shapeless"); }

    /**
     * Add an ingredient item.
     * @param id The item ID.
     * @return this
     */
    public ShapelessRecipeBuilder ingredientItem(String id) {
        IdUtils.validateIdentifier(id);
        withArray("ingredients", ingredients ->
            ingredients.add(new JsonObjectBuilder().set("item", id.toString()).build()));
        return this;
    }

    /**
     * Add an ingredient item as any of the given tag.
     * @param id The tag ID.
     * @return this
     */
    public ShapelessRecipeBuilder ingredientTag(String id) {
        IdUtils.validateIdentifier(id);
        withArray("ingredients", ingredients ->
            ingredients.add(new JsonObjectBuilder().set("tag", id.toString()).build()));
        return this;
    }

    /**
     * Add an ingredient item as one of a list of options.
     * @param settings A callback which will be passed a {@link MultiIngredientBuilder}.
     * @return this
     */
    public ShapelessRecipeBuilder multiIngredient(Processor<MultiIngredientBuilder> settings) {
        withArray("ingredients", ingredients ->
            ingredients.add(settings.process(new MultiIngredientBuilder()).build()));
        return this;
    }

    /**
     * Set the item produced by this recipe.
     * @param id The item ID.
     * @param count The number of result items.
     * @return this
     */
    public ShapelessRecipeBuilder result(String id, int count) {
        IdUtils.validateIdentifier(id);
        this.set("result", new JsonObjectBuilder().set("item", id.toString()).set("count", count).build());
        return this;
    }
}
