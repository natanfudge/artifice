package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config;

public class CountNoiseBiasedDecoratorConfigBuilder extends DecoratorConfigBuilder {

    public CountNoiseBiasedDecoratorConfigBuilder() {
        super();
    }

    public CountNoiseBiasedDecoratorConfigBuilder noiseToCountRatio(int noiseToCountRatio) {
        this.root.addProperty("noise_to_count_ratio", noiseToCountRatio);
        return this;
    }

    public CountNoiseBiasedDecoratorConfigBuilder noiseFactor(double noiseFactor) {
        this.root.addProperty("noise_factor", noiseFactor);
        return this;
    }

    public CountNoiseBiasedDecoratorConfigBuilder noiseOffset(double noiseOffset) {
        this.root.addProperty("noise_offset", noiseOffset);
        return this;
    }
}
