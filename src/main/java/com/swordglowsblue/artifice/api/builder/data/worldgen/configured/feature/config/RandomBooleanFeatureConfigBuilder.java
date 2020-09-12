package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config;

public class RandomBooleanFeatureConfigBuilder extends FeatureConfigBuilder {

    public RandomBooleanFeatureConfigBuilder() {
        super();
    }

    public RandomBooleanFeatureConfigBuilder featureOnTrue(String configuredFeatureID) {
        this.root.addProperty("feature_true", configuredFeatureID);
        return this;
    }

    public RandomBooleanFeatureConfigBuilder featureOnFalse(String configuredFeatureID) {
        this.root.addProperty("feature_false", configuredFeatureID);
        return this;
    }
}
