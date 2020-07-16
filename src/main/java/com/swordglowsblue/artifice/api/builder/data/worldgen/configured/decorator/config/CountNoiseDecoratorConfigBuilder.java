package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config;

public class CountNoiseDecoratorConfigBuilder extends DecoratorConfigBuilder {

    public CountNoiseDecoratorConfigBuilder() {
        super();
    }

    public CountNoiseDecoratorConfigBuilder noiseLevel(double noiseLevel) {
        this.root.addProperty("noise_level", noiseLevel);
        return this;
    }

    public CountNoiseDecoratorConfigBuilder belowNoise(int belowNoise) {
        this.root.addProperty("below_noise", belowNoise);
        return this;
    }

    public CountNoiseDecoratorConfigBuilder aboveNoise(int aboveNoise) {
        this.root.addProperty("above_noise", aboveNoise);
        return this;
    }
}
