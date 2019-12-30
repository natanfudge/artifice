package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import com.swordglowsblue.artifice.api.util.Processor;
import net.minecraft.util.Identifier;

/**
 * Builder for a stonecutting recipe ({@code namespace:recipes/id.json}).
 * @see <a href="https://minecraft.gamepedia.com/Recipe#JSON_format" target="_blank">Minecraft Wiki</a>
 */
public final class StonecuttingRecipeBuilder extends RecipeBuilder<StonecuttingRecipeBuilder> {
    public StonecuttingRecipeBuilder() { super(); type(new Identifier("stonecutting")); }

    /**
     * Set the item being cut.
     * @param id The item ID.
     * @return this
     */
    public StonecuttingRecipeBuilder ingredientItem(Identifier id) {
        root.add("ingredient", new JsonObjectBuilder().add("item", id.toString()).build());
        return this;
    }

    /**
     * Set the item being cut as any of the given tag.
     * @param id The tag ID.
     * @return this
     */
    public StonecuttingRecipeBuilder ingredientTag(Identifier id) {
        root.add("ingredient", new JsonObjectBuilder().add("tag", id.toString()).build());
        return this;
    }

    /**
     * Set the item being cut as one of a list of options.
     * @param settings A callback which will be passed a {@link MultiIngredientBuilder}.
     * @return this
     */
    public StonecuttingRecipeBuilder multiIngredient(Processor<MultiIngredientBuilder> settings) {
        root.add("ingredient", settings.process(new MultiIngredientBuilder()).build());
        return this;
    }

    /**
     * Set the item produced by this recipe.
     * @param id The item ID.
     * @return this
     */
    public StonecuttingRecipeBuilder result(Identifier id) {
        root.addProperty("result", id.toString());
        return this;
    }

    /**
     * Set the number of items produced by this recipe.
     * @param count The number of result items.
     * @return this
     */
    public StonecuttingRecipeBuilder count(int count) {
        root.addProperty("count", count);
        return this;
    }
}
