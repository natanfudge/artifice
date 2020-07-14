package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config;

public class ProbabilityConfigBuilder extends FeatureConfigBuilder {

    public ProbabilityConfigBuilder() {
        super();
    }

    public ProbabilityConfigBuilder probability(float probability) {
        if (probability > 1.0F) throw new IllegalArgumentException("probability can't be higher than 1.0F! Found " + probability);
        if (probability < 0.0F) throw new IllegalArgumentException("probability can't be smaller than 0.0F! Found " + probability);
        this.root.addProperty("probability", probability);
        return this;
    }
}
