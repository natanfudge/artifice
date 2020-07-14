package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config;

public class TopSolidHeightmapNoiseBiasedDecoratorConfigBuilder extends DecoratorConfigBuilder {

    public TopSolidHeightmapNoiseBiasedDecoratorConfigBuilder() {
        super();
    }

    public TopSolidHeightmapNoiseBiasedDecoratorConfigBuilder noiseToCountRatio(int noiseToCountRatio) {
        this.root.addProperty("noise_to_count_ratio", noiseToCountRatio);
        return this;
    }

    public TopSolidHeightmapNoiseBiasedDecoratorConfigBuilder noiseFactor(double noiseFactor) {
        this.root.addProperty("noise_factor", noiseFactor);
        return this;
    }

    public TopSolidHeightmapNoiseBiasedDecoratorConfigBuilder noiseOffset(double noiseOffset) {
        this.root.addProperty("noise_offset", noiseOffset);
        return this;
    }
}
