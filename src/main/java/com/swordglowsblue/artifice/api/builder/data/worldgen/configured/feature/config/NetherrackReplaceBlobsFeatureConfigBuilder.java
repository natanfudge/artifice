package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.class_5428Builder;
import com.swordglowsblue.artifice.api.util.Processor;

public class NetherrackReplaceBlobsFeatureConfigBuilder extends FeatureConfigBuilder {

    public NetherrackReplaceBlobsFeatureConfigBuilder() {
        super();
    }

    public NetherrackReplaceBlobsFeatureConfigBuilder radius(Processor<class_5428Builder> processor) {
        with("radius", JsonObject::new, jsonObject -> processor.process(new class_5428Builder()).buildTo(jsonObject));
        return this;
    }

    public NetherrackReplaceBlobsFeatureConfigBuilder target(Processor<StateDataBuilder> processor) {
        with("target", JsonObject::new, jsonObject -> processor.process(new StateDataBuilder()).buildTo(jsonObject));
        return this;
    }

    public NetherrackReplaceBlobsFeatureConfigBuilder state(Processor<StateDataBuilder> processor) {
        with("state", JsonObject::new, jsonObject -> processor.process(new StateDataBuilder()).buildTo(jsonObject));
        return this;
    }
}
