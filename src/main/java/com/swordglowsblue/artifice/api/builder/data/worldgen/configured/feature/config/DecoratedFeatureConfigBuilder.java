package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.ConfiguredDecoratorBuilder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.ConfiguredSubFeatureBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class DecoratedFeatureConfigBuilder extends FeatureConfigBuilder {

    public DecoratedFeatureConfigBuilder() {
        super();
    }

    public DecoratedFeatureConfigBuilder feature(Processor<ConfiguredSubFeatureBuilder> processor) {
        with("feature", JsonObject::new, jsonObject -> processor.process(new ConfiguredSubFeatureBuilder()).buildTo(jsonObject));
        return this;
    }

    public DecoratedFeatureConfigBuilder feature(String configuredFeatureID) {
        this.root.addProperty("feature", configuredFeatureID);
        return this;
    }

    public DecoratedFeatureConfigBuilder decorator(Processor<ConfiguredDecoratorBuilder> processor) {
        with("decorator", JsonObject::new, jsonObject -> processor.process(new ConfiguredDecoratorBuilder()).buildTo(jsonObject));
        return this;
    }

    public DecoratedFeatureConfigBuilder decorator(String configuredDecoratorID) {
        this.root.addProperty("decorator", configuredDecoratorID);
        return this;
    }
}
