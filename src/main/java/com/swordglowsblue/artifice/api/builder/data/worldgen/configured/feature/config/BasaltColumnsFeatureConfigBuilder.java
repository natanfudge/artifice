package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.data.worldgen.class_5428Builder;
import com.swordglowsblue.artifice.api.util.Processor;

public class BasaltColumnsFeatureConfigBuilder extends FeatureConfigBuilder {

    public BasaltColumnsFeatureConfigBuilder() {
        super();
    }

    public BasaltColumnsFeatureConfigBuilder reach(Processor<class_5428Builder> processor) {
        with("reach", JsonObject::new, jsonObject -> processor.process(new class_5428Builder()).buildTo(jsonObject));
        return this;
    }

    public BasaltColumnsFeatureConfigBuilder height(Processor<class_5428Builder> processor) {
        with("height", JsonObject::new, jsonObject -> processor.process(new class_5428Builder()).buildTo(jsonObject));
        return this;
    }
}
