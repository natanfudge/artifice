package com.swordglowsblue.artifice.api.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.util.Processor;

public class JsonObjectBuilder extends TypedJsonBuilder<JsonObject> {
    public JsonObjectBuilder() { super(new JsonObject(), j->j); }
    public JsonObjectBuilder(JsonObject root) { super(root, j->j); }

    public JsonObjectBuilder add(String name, JsonElement value) {
        root.add(name, value);
        return this;
    }

    public JsonObjectBuilder add(String name, String value) {
        root.addProperty(name, value);
        return this;
    }

    public JsonObjectBuilder add(String name, boolean value) {
        root.addProperty(name, value);
        return this;
    }

    public JsonObjectBuilder add(String name, Number value) {
        root.addProperty(name, value);
        return this;
    }

    public JsonObjectBuilder add(String name, Character value) {
        root.addProperty(name, value);
        return this;
    }

    public JsonObjectBuilder addObject(String name, Processor<JsonObjectBuilder> settings) {
        root.add(name, settings.process(new JsonObjectBuilder()).build());
        return this;
    }

    public JsonObjectBuilder addArray(String name, Processor<JsonArrayBuilder> settings) {
        root.add(name, settings.process(new JsonArrayBuilder()).build());
        return this;
    }
}
