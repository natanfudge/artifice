package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config;

public class CountDepthDecoratorConfigBuilder extends DecoratorConfigBuilder {

    public CountDepthDecoratorConfigBuilder() {
        super();
    }

    public CountDepthDecoratorConfigBuilder count(int count) {
        this.root.addProperty("baseline", count);
        return this;
    }

    public CountDepthDecoratorConfigBuilder spread(int spread) {
        this.root.addProperty("spread", spread);
        return this;
    }
}
