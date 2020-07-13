package com.swordglowsblue.artifice.api.builder.data;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;

public class StateDataBuilder extends TypedJsonBuilder<JsonObject> {

    private JsonObject jsonObject = new JsonObject();

    public StateDataBuilder() {
        super(new JsonObject(), j->j);
    }

    /**
     * Set the id of the block.
     * @param id
     * @return
     */
    public StateDataBuilder name(String id) {
        this.root.addProperty("Name", id);
        return this;
    }

    /**
     * Set a property to a state.
     * @param property
     * @param state
     * @return
     */
    public StateDataBuilder setProperty(String property, String state) {
        this.jsonObject.addProperty(property, state);
        this.root.add("Properties", this.jsonObject);
        return this;
    }
}
