package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config.FeatureConfigBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;

public class ConfiguredSubFeatureBuilder extends TypedJsonBuilder<JsonObject> {
    public ConfiguredSubFeatureBuilder() {
        super(new JsonObject(), j->j);
    }

    public ConfiguredSubFeatureBuilder featureID(String id) {
        this.root.addProperty("type", id);
        return this;
    }

    public <C extends FeatureConfigBuilder> ConfiguredSubFeatureBuilder featureConfig(Processor<C> processor, C instance) {
        with("config", JsonObject::new, jsonObject -> processor.process(instance).buildTo(jsonObject));
        return this;
    }

    public ConfiguredSubFeatureBuilder defaultConfig() {
        return this.featureConfig(featureConfigBuilder -> {} , new FeatureConfigBuilder());
    }
}
