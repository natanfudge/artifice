package com.swordglowsblue.artifice.impl.resource_types;

public class BlockModelBuilder extends ArtificeModelResource.Builder<BlockModelBuilder> {
    public BlockModelBuilder ambientocclusion(boolean ambientocclusion) {
        this.model.addProperty("ambientocclusion", ambientocclusion);
        return this;
    }
}
