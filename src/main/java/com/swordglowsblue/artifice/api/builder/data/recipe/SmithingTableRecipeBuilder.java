package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import net.minecraft.util.Identifier;

public class SmithingTableRecipeBuilder extends RecipeBuilder<SmithingTableRecipeBuilder> {
    public SmithingTableRecipeBuilder() {
        super();
        type(new Identifier("smithing"));
    }

    public SmithingTableRecipeBuilder baseItem(Identifier id) {
        root.add("base", item(id));
        return this;
    }

    public SmithingTableRecipeBuilder baseTag(Identifier id) {
        root.add("base", tag(id));
        return this;
    }

    public SmithingTableRecipeBuilder additionItem(Identifier id) {
        root.add("addition", item(id));
        return this;
    }

    public SmithingTableRecipeBuilder additionTag(Identifier id) {
        root.add("addition", tag(id));
        return this;
    }

    public SmithingTableRecipeBuilder result(Identifier id) {
        root.add("result", new JsonPrimitive(id.toString()));
        return this;
    }

    private JsonObject item(Identifier id) {
        return new JsonObjectBuilder().add("item", id.toString()).build();
    }

    private JsonObject tag(Identifier id) {
        return new JsonObjectBuilder().add("tag", id.toString()).build();
    }
}
