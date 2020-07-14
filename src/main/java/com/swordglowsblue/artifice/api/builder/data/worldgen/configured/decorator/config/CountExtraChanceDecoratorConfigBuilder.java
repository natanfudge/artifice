package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config;

public class CountExtraChanceDecoratorConfigBuilder extends DecoratorConfigBuilder {

    public CountExtraChanceDecoratorConfigBuilder() {
        super();
    }

    public CountExtraChanceDecoratorConfigBuilder count(int count) {
        this.root.addProperty("count", count);
        return this;
    }

    public CountExtraChanceDecoratorConfigBuilder extraCount(int extraCount) {
        this.root.addProperty("extra_count", extraCount);
        return this;
    }

    public CountExtraChanceDecoratorConfigBuilder extraChance(float extraChance) {
        this.root.addProperty("extra_chance", extraChance);
        return this;
    }
}
