package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class RandomFeatureConfigBuilder extends FeatureConfigBuilder {

    public RandomFeatureConfigBuilder() {
        super();
        this.root.add("features", new JsonArray());
    }

    public RandomFeatureConfigBuilder defaultConfiguredFeature(String configuredFeatureID) {
        this.root.addProperty("default", configuredFeatureID);
        return this;
    }

    public RandomFeatureConfigBuilder addConfiguredFeature(Processor<RandomFeatureEntryBuilder> processor) {
        this.root.getAsJsonArray("features").add(processor.process(new RandomFeatureEntryBuilder()).buildTo(new JsonObject()));
        return this;
    }

    public static class RandomFeatureEntryBuilder extends TypedJsonBuilder<JsonObject> {

        public RandomFeatureEntryBuilder() {
            super(new JsonObject(), j->j);
        }

        public RandomFeatureEntryBuilder chance(float chance) {
            if (chance > 1.0F) throw new IllegalArgumentException("chance can't be higher than 1.0F! Found " + chance);
            if (chance < 0.0F) throw new IllegalArgumentException("chance can't be smaller than 0.0F! Found " + chance);
            this.root.addProperty("chance", chance);
            return this;
        }

        public RandomFeatureEntryBuilder configuredFeatureID(String configuredFeatureID) {
            this.root.addProperty("feature", configuredFeatureID);
            return this;
        }
    }
}
