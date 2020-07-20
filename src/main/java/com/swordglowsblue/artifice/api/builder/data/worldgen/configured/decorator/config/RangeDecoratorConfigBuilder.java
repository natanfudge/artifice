package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config;

public class RangeDecoratorConfigBuilder extends DecoratorConfigBuilder {

    public RangeDecoratorConfigBuilder() {
        super();
    }

    public RangeDecoratorConfigBuilder bottomOffset(int bottomOffset) {
        this.root.addProperty("bottom_offset", bottomOffset);
        return this;
    }

    public RangeDecoratorConfigBuilder topOffset(int topOffset) {
        this.root.addProperty("top_offset", topOffset);
        return this;
    }

    public RangeDecoratorConfigBuilder maximum(int maximum) {
        this.root.addProperty("maximum", maximum);
        return this;
    }
}
