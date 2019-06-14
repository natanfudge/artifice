package com.swordglowsblue.artifice.impl.resource_types;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ModelDisplayBuilder {
    private final JsonObject display;
    ModelDisplayBuilder(JsonObject display) { this.display = display; }

    public ModelDisplayBuilder rotation(int x, int y, int z) {
        JsonArray rotation = new JsonArray();
        rotation.add(x);
        rotation.add(y);
        rotation.add(z);
        display.add("rotation", rotation);
        return this;
    }

    public ModelDisplayBuilder translation(int x, int y, int z) {
        JsonArray translation = new JsonArray();
        translation.add(x);
        translation.add(y);
        translation.add(z);
        display.add("translation", translation);
        return this;
    }

    public ModelDisplayBuilder scale(int x, int y, int z) {
        JsonArray scale = new JsonArray();
        scale.add(x);
        scale.add(y);
        scale.add(z);
        display.add("scale", scale);
        return this;
    }

    JsonObject build() { return display; }
}
