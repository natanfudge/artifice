package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config;

public class CountExtraDecoratorConfigBuilder extends DecoratorConfigBuilder {

    public CountExtraDecoratorConfigBuilder() {
        super();
    }

    public CountExtraDecoratorConfigBuilder count(int count) {
        this.root.addProperty("count", count);
        return this;
    }

    public CountExtraDecoratorConfigBuilder extraCount(int extraCount) {
        this.root.addProperty("extra_count", extraCount);
        return this;
    }

    public CountExtraDecoratorConfigBuilder extraChance(float extraChance) {
        this.root.addProperty("extra_chance", extraChance);
        return this;
    }
}
