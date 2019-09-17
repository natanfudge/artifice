package artificemc.artifice.api.builders.server;

import artificemc.artifice.api.builders.JsonObjectBuilder;
import artificemc.artifice.api.core.util.Processor;
import artificemc.artifice.impl.core.util.IdUtils;

/**
 * Builder for cooking recipes ({@code namespace:recipes/id.json}).
 * Used for all types of cooking (smelting, blasting, smoking, campfire_cooking).
 * @see <a href="https://minecraft.gamepedia.com/Recipe#JSON_format" target="_blank">Minecraft Wiki</a>
 */
public final class CookingRecipeBuilder extends RecipeBuilder<CookingRecipeBuilder> {
    /**
     * Set the item being cooked.
     * @param id The item ID.
     * @return this
     */
    public CookingRecipeBuilder ingredientItem(String id) {
        IdUtils.validateIdentifier(id);
        this.set("ingredient", new JsonObjectBuilder().set("item", id).build());
        return this;
    }

    /**
     * Set the item being cooked as any of the given tag.
     * @param id The tag ID.
     * @return this
     */
    public CookingRecipeBuilder ingredientTag(String id) {
        IdUtils.validateIdentifier(id);
        this.set("ingredient", new JsonObjectBuilder().set("tag", id).build());
        return this;
    }

    /**
     * Set the item being cooked as one of a list of options.
     * @param settings A callback which will be passed a {@link MultiIngredientBuilder}.
     * @return this
     */
    public CookingRecipeBuilder multiIngredient(Processor<MultiIngredientBuilder> settings) {
        this.set("ingredient", settings.process(new MultiIngredientBuilder()).build());
        return this;
    }

    /**
     * Set the item produced by this recipe.
     * @param id The item ID.
     * @return this
     */
    public CookingRecipeBuilder result(String id) {
        IdUtils.validateIdentifier(id);
        this.set("result", id);
        return this;
    }

    /**
     * Set the amount of experience given by this recipe.
     * @param exp The amount of experience.
     * @return this
     */
    public CookingRecipeBuilder experience(double exp) {
        this.set("experience", exp);
        return this;
    }

    /**
     * Set how long this recipe should take to complete in ticks.
     * @param time The number of ticks.
     * @return this
     */
    public CookingRecipeBuilder cookingTime(int time) {
        this.set("cookingtime", time);
        return this;
    }
}
