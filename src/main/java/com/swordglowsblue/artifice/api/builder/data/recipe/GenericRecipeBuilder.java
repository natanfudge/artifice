package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonElement;
import com.swordglowsblue.artifice.api.builder.JsonArrayBuilder;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public final class GenericRecipeBuilder extends RecipeBuilder<GenericRecipeBuilder> {
    public RecipeBuilder add(String name, JsonElement value) {
        root.add(name, value);
        return this;
    }

    public RecipeBuilder add(String name, String value) {
        root.addProperty(name, value);
        return this;
    }

    public RecipeBuilder add(String name, boolean value) {
        root.addProperty(name, value);
        return this;
    }

    public RecipeBuilder add(String name, Number value) {
        root.addProperty(name, value);
        return this;
    }

    public RecipeBuilder add(String name, Character value) {
        root.addProperty(name, value);
        return this;
    }

    public RecipeBuilder addObject(String name, Processor<JsonObjectBuilder> settings) {
        root.add(name, settings.process(new JsonObjectBuilder()).build());
        return this;
    }

    public RecipeBuilder addArray(String name, Processor<JsonArrayBuilder> settings) {
        root.add(name, settings.process(new JsonArrayBuilder()).build());
        return this;
    }
}
