package com.swordglowsblue.artifice.api.builder.data;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.builder.data.dimension.GeneratorSettingsBuilder;

public class BlockStateBuilder extends TypedJsonBuilder<JsonObject> {

    private JsonObject jsonObject = new JsonObject();

    public BlockStateBuilder() {
        super(new JsonObject(), j->j);
    }

    /**
     * Set the id of the block.
     * @param id
     * @return
     */
    public BlockStateBuilder name(String id) {
        this.root.addProperty("Name", id);
        return this;
    }

    /**
     * Set a property to a state.
     * @param property
     * @param state
     * @return
     */
    public BlockStateBuilder setProperty(String property, String state) {
        this.jsonObject.addProperty(property, state);
        this.root.add("Properties", this.jsonObject);
        return this;
    }
}
