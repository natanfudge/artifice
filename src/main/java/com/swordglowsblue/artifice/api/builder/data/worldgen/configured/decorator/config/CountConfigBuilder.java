package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.data.worldgen.class_5428Builder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config.FeatureConfigBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class CountConfigBuilder extends DecoratorConfigBuilder {
    public CountConfigBuilder() {
        super();
    }

    public CountConfigBuilder count(int count) {
        this.root.addProperty("count", count);
        return this;
    }

    public CountConfigBuilder count(Processor<class_5428Builder> processor) {
        with("count", JsonObject::new, jsonObject -> processor.process(new class_5428Builder()).buildTo(jsonObject));
        return this;
    }
}
