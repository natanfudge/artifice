package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config;

import com.google.gson.JsonArray;

public class SimpleRandomFeatureConfigBuilder extends FeatureConfigBuilder {

    public SimpleRandomFeatureConfigBuilder() {
        super();
        this.root.add("features", new JsonArray());
    }

    public SimpleRandomFeatureConfigBuilder addConfiguredFeature(String configuredFeatureID) {
        this.root.getAsJsonArray("features").add(configuredFeatureID);
        return this;
    }
}
