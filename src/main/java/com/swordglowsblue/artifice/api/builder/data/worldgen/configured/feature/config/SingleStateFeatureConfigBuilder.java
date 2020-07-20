package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class SingleStateFeatureConfigBuilder extends FeatureConfigBuilder {

    public SingleStateFeatureConfigBuilder() {
        super();
    }

    public SingleStateFeatureConfigBuilder state(Processor<StateDataBuilder> processor) {
        with("state", JsonObject::new, jsonObject -> processor.process(new StateDataBuilder()).buildTo(jsonObject));
        return this;
    }
}
