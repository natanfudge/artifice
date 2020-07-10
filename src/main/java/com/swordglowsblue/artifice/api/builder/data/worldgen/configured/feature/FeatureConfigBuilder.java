package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;

public class FeatureConfigBuilder extends TypedJsonBuilder<JsonObject> {

    public FeatureConfigBuilder() {
        super(new JsonObject(), j->j);
    }

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
}
