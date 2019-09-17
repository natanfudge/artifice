package artificemc.artifice.api.builders.server;

import artificemc.artifice.api.builders.JsonObjectBuilder;
import artificemc.artifice.api.core.util.Processor;
import artificemc.artifice.impl.core.util.IdUtils;

/**
 * Builder for a stonecutting recipe ({@code namespace:recipes/id.json}).
 * @see <a href="https://minecraft.gamepedia.com/Recipe#JSON_format" target="_blank">Minecraft Wiki</a>
 */
public final class StonecuttingRecipeBuilder extends RecipeBuilder<StonecuttingRecipeBuilder> {
    public StonecuttingRecipeBuilder() { super(); type("stonecutting"); }

    /**
     * Set the item being cut.
     * @param id The item ID.
     * @return this
     */
    public StonecuttingRecipeBuilder ingredientItem(String id) {
        IdUtils.validateIdentifier(id);
        this.set("ingredient", new JsonObjectBuilder().set("item", id.toString()).build());
        return this;
    }

    /**
     * Set the item being cut as any of the given tag.
     * @param id The tag ID.
     * @return this
     */
    public StonecuttingRecipeBuilder ingredientTag(String id) {
        IdUtils.validateIdentifier(id);
        this.set("ingredient", new JsonObjectBuilder().set("tag", id.toString()).build());
        return this;
    }

    /**
     * Set the item being cut as one of a list of options.
     * @param settings A callback which will be passed a {@link MultiIngredientBuilder}.
     * @return this
     */
    public StonecuttingRecipeBuilder multiIngredient(Processor<MultiIngredientBuilder> settings) {
        this.set("ingredient", settings.process(new MultiIngredientBuilder()).build());
        return this;
    }

    /**
     * Set the item produced by this recipe.
     * @param id The item ID.
     * @return this
     */
    public StonecuttingRecipeBuilder result(String id) {
        IdUtils.validateIdentifier(id);
        this.set("result", id.toString());
        return this;
    }

    /**
     * Set the number of items produced by this recipe.
     * @param count The number of result items.
     * @return this
     */
    public StonecuttingRecipeBuilder count(int count) {
        this.set("count", count);
        return this;
    }
}
