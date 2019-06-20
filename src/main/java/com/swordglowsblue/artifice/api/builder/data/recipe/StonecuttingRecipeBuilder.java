package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import com.swordglowsblue.artifice.api.util.Processor;
import net.minecraft.util.Identifier;

public final class StonecuttingRecipeBuilder extends RecipeBuilder<StonecuttingRecipeBuilder> {
    public StonecuttingRecipeBuilder() { super(); type(new Identifier("stonecutting")); }

    public StonecuttingRecipeBuilder ingredientItem(Identifier id) {
        root.add("ingredient", new JsonObjectBuilder().add("item", id.toString()).build());
        return this;
    }

    public StonecuttingRecipeBuilder ingredientTag(Identifier id) {
        root.add("ingredient", new JsonObjectBuilder().add("tag", id.toString()).build());
        return this;
    }

    public StonecuttingRecipeBuilder multiIngredient(Processor<MultiIngredientBuilder> settings) {
        root.add("ingredient", settings.process(new MultiIngredientBuilder()).build());
        return this;
    }

    public StonecuttingRecipeBuilder result(Identifier id) {
        root.addProperty("result", id.toString());
        return this;
    }

    public StonecuttingRecipeBuilder count(int count) {
        root.addProperty("count", count);
        return this;
    }
}
