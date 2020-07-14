package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config;

import com.google.gson.JsonArray;

public class EndGatewayFeatureConfigBuilder extends FeatureConfigBuilder {

    public EndGatewayFeatureConfigBuilder() {
        super();
    }

    public EndGatewayFeatureConfigBuilder exit(int x, int y, int z) {
        this.root.add("exit", new JsonArray());
        this.root.getAsJsonArray("exit").add(x);
        this.root.getAsJsonArray("exit").add(y);
        this.root.getAsJsonArray("exit").add(z);
        return this;
    }

    public EndGatewayFeatureConfigBuilder exact(boolean exact) {
        this.root.addProperty("exact", exact);
        return this;
    }
}
