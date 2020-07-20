package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.UniformIntDistributionBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class DeltaFeatureConfigBuilder extends FeatureConfigBuilder {

    public DeltaFeatureConfigBuilder() {
        super();
    }

    public DeltaFeatureConfigBuilder size(Processor<UniformIntDistributionBuilder> processor) {
        with("size", JsonObject::new, jsonObject -> processor.process(new UniformIntDistributionBuilder()).buildTo(jsonObject));
        return this;
    }

    public DeltaFeatureConfigBuilder rimSize(Processor<UniformIntDistributionBuilder> processor) {
        with("rim_size", JsonObject::new, jsonObject -> processor.process(new UniformIntDistributionBuilder()).buildTo(jsonObject));
        return this;
    }

    public DeltaFeatureConfigBuilder rim(Processor<StateDataBuilder> processor) {
        with("rim", JsonObject::new, jsonObject -> processor.process(new StateDataBuilder()).buildTo(jsonObject));
        return this;
    }

    public DeltaFeatureConfigBuilder contents(Processor<StateDataBuilder> processor) {
        with("contents", JsonObject::new, jsonObject -> processor.process(new StateDataBuilder()).buildTo(jsonObject));
        return this;
    }
}
