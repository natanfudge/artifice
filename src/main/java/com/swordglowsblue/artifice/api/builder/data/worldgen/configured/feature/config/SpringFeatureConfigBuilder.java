package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class SpringFeatureConfigBuilder extends FeatureConfigBuilder {

    public SpringFeatureConfigBuilder() {
        super();
        this.root.add("valid_blocks", new JsonArray());
    }

    public SpringFeatureConfigBuilder fluidState(Processor<StateDataBuilder> processor) {
        with("state", JsonObject::new, jsonObject -> processor.process(new StateDataBuilder()).buildTo(jsonObject));
        return this;
    }

    public SpringFeatureConfigBuilder addValidBlock(String blockID) {
        this.root.getAsJsonArray("valid_blocks").add(blockID);
        return this;
    }

    public SpringFeatureConfigBuilder requiresBlockBelow(boolean requiresBlockBelow) {
        this.root.addProperty("requires_block_below", requiresBlockBelow);
        return this;
    }

    public SpringFeatureConfigBuilder rockCount(int rockCount) {
        this.root.addProperty("rock_count", rockCount);
        return this;
    }

    public SpringFeatureConfigBuilder holeCount(int holeCount) {
        this.root.addProperty("hole_count", holeCount);
        return this;
    }
}
