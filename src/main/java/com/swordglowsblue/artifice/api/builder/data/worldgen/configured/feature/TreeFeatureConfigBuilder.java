package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature;

public class TreeFeatureConfigBuilder extends FeatureConfigBuilder {

    public TreeFeatureConfigBuilder() {
        super();
    }

    public TreeFeatureConfigBuilder maxWaterDepth(int maxWaterDepth) {
        this.root.addProperty("max_water_depth", maxWaterDepth);
        return this;
    }

    public TreeFeatureConfigBuilder ignoreVines(boolean ignoreVines) {
        this.root.addProperty("ignore_vines", ignoreVines);
        return this;
    }
}
