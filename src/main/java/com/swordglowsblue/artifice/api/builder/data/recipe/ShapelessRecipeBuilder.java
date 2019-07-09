package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonArray;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import com.swordglowsblue.artifice.api.util.Processor;
import net.minecraft.util.Identifier;

/**
 * Builder for a shapeless crafting recipe ({@code namespace:recipes/id.json}).
 * @see <a href="https://minecraft.gamepedia.com/Recipe#JSON_format" target="_blank">Minecraft Wiki</a>
 */
public final class ShapelessRecipeBuilder extends RecipeBuilder<ShapelessRecipeBuilder> {
    public ShapelessRecipeBuilder() { super(); type(new Identifier("crafting_shapeless")); }

    /**
     * Add an ingredient item.
     * @param id The item ID.
     * @return this
     */
    public ShapelessRecipeBuilder ingredientItem(Identifier id) {
        with("ingredients", JsonArray::new, ingredients ->
            ingredients.add(new JsonObjectBuilder().add("item", id.toString()).build()));
        return this;
    }

    /**
     * Add an ingredient item as any of the given tag.
     * @param id The tag ID.
     * @return this
     */
    public ShapelessRecipeBuilder ingredientTag(Identifier id) {
        with("ingredients", JsonArray::new, ingredients ->
            ingredients.add(new JsonObjectBuilder().add("tag", id.toString()).build()));
        return this;
    }

    /**
     * Add an ingredient item as one of a list of options.
     * @param settings A callback which will be passed a {@link MultiIngredientBuilder}.
     * @return this
     */
    public ShapelessRecipeBuilder multiIngredient(Processor<MultiIngredientBuilder> settings) {
        with("ingredients", JsonArray::new, ingredients ->
            ingredients.add(settings.process(new MultiIngredientBuilder()).build()));
        return this;
    }

    /**
     * Set the item produced by this recipe.
     * @param id The item ID.
     * @param count The number of result items.
     * @return this
     */
    public ShapelessRecipeBuilder result(Identifier id, int count) {
        root.add("result", new JsonObjectBuilder().add("item", id.toString()).add("count", count).build());
        return this;
    }
}
