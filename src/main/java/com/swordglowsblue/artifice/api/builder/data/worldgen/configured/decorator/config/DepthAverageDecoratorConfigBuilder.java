package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config;

public class DepthAverageDecoratorConfigBuilder extends DecoratorConfigBuilder {

    public DepthAverageDecoratorConfigBuilder() {
        super();
    }

    public DepthAverageDecoratorConfigBuilder count(int count) {
        this.root.addProperty("baseline", count);
        return this;
    }

    public DepthAverageDecoratorConfigBuilder spread(int spread) {
        this.root.addProperty("spread", spread);
        return this;
    }
}
