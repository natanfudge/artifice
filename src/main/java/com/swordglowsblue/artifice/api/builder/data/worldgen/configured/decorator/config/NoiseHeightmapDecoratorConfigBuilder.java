package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config;

public class NoiseHeightmapDecoratorConfigBuilder extends DecoratorConfigBuilder {

    public NoiseHeightmapDecoratorConfigBuilder() {
        super();
    }

    public NoiseHeightmapDecoratorConfigBuilder noiseLevel(double noiseLevel) {
        this.root.addProperty("noise_level", noiseLevel);
        return this;
    }

    public NoiseHeightmapDecoratorConfigBuilder belowNoise(int belowNoise) {
        this.root.addProperty("below_noise", belowNoise);
        return this;
    }

    public NoiseHeightmapDecoratorConfigBuilder aboveNoise(int aboveNoise) {
        this.root.addProperty("above_noise", aboveNoise);
        return this;
    }
}
