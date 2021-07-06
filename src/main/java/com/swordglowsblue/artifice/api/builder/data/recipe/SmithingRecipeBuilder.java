package com.swordglowsblue.artifice.api.builder.data.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import net.minecraft.util.Identifier;

/**
 * Builder for a smithing recipe ({@code namespace:recipes/id.json}).
 * @see <a href="https://minecraft.gamepedia.com/Recipe#JSON_format" target="_blank">Minecraft Wiki</a>
 */
public class SmithingRecipeBuilder extends RecipeBuilder<SmithingRecipeBuilder> {
    public SmithingRecipeBuilder() {
        super();
        type(new Identifier("smithing"));
    }

    /**
     * Set the item being smithed
     * @param id The item ID
     * @return this
     * */
    public SmithingRecipeBuilder baseItem(Identifier id) {
        root.add("base", item(id));
        return this;
    }

    /**
     * Set the item being smithed to be any one of the given tag
     * @param id The tag ID
     * @return this
     * */
    public SmithingRecipeBuilder baseTag(Identifier id) {
        root.add("base", tag(id));
        return this;
    }

    /**
     * Set the item to be added on during the smithing
     * @param id The item ID
     * @return this
     * */
    public SmithingRecipeBuilder additionItem(Identifier id) {
        root.add("addition", item(id));
        return this;
    }

    /**
     * Set the item to be added on to be any one of the given tag
     * @param id The ta ID
     * @return this
     * */
    public SmithingRecipeBuilder additionTag(Identifier id) {
        root.add("addition", tag(id));
        return this;
    }

    /**
     * Set the result of the smithing.
     * Item NBT will be preserved.
     * @param id The ID of the resulting item
     * @return this
     * */
    public SmithingRecipeBuilder result(Identifier id) {
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
