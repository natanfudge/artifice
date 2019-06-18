package com.swordglowsblue.artifice.api.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.swordglowsblue.artifice.api.util.Processor;

public final class JsonArrayBuilder {
    private final JsonArray root;

    public JsonArrayBuilder() { this(new JsonArray()); }
    public JsonArrayBuilder(JsonArray root) { this.root = root; }

    public JsonArray build() { return buildTo(new JsonArray()); }
    public JsonArray buildTo(JsonArray target) {
        target.addAll(root);
        return target;
    }

    public JsonArrayBuilder add(JsonElement value) {
        root.add(value);
        return this;
    }

    public JsonArrayBuilder add(String value) {
        root.add(value);
        return this;
    }

    public JsonArrayBuilder add(boolean value) {
        root.add(value);
        return this;
    }

    public JsonArrayBuilder add(Number value) {
        root.add(value);
        return this;
    }

    public JsonArrayBuilder add(Character value) {
        root.add(value);
        return this;
    }

    public JsonArrayBuilder addObject(Processor<JsonObjectBuilder> settings) {
        root.add(settings.process(new JsonObjectBuilder()).build());
        return this;
    }

    public JsonArrayBuilder addArray(Processor<JsonArrayBuilder> settings) {
        root.add(settings.process(new JsonArrayBuilder()).build());
        return this;
    }
}
