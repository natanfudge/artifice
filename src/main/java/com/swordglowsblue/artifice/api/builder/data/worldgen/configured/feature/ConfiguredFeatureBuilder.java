package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config.FeatureConfigBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;

public class ConfiguredFeatureBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public ConfiguredFeatureBuilder() {
        super(new JsonObject(), JsonResource::new);
    }

    public ConfiguredFeatureBuilder featureID(String id) {
        this.root.addProperty("type", id);
        return this;
    }

    public <C extends FeatureConfigBuilder> ConfiguredFeatureBuilder featureConfig(Processor<C> processor, C instance) {
        with("config", JsonObject::new, jsonObject -> processor.process(instance).buildTo(jsonObject));
        return this;
    }

    public ConfiguredFeatureBuilder defaultConfig() {
        return this.featureConfig(featureConfigBuilder -> {} , new FeatureConfigBuilder());
    }
}
