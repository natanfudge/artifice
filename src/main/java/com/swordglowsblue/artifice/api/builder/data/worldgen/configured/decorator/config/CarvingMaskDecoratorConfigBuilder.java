package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config;

import net.minecraft.world.gen.GenerationStep;

public class CarvingMaskDecoratorConfigBuilder extends DecoratorConfigBuilder {

    public CarvingMaskDecoratorConfigBuilder() {
        super();
    }

    public CarvingMaskDecoratorConfigBuilder probability(float probability) {
        this.root.addProperty("probability", probability);
        return this;
    }

    public CarvingMaskDecoratorConfigBuilder step(GenerationStep.Carver step) {
        this.root.addProperty("step", step.getName());
        return this;
    }
}
