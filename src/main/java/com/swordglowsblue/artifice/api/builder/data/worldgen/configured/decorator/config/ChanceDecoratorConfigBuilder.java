package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config;

public class ChanceDecoratorConfigBuilder extends DecoratorConfigBuilder {

    public ChanceDecoratorConfigBuilder() {
        super();
    }

    public ChanceDecoratorConfigBuilder chance(int chance) {
        this.root.addProperty("chance", chance);
        return this;
    }
}
