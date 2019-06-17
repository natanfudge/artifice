package com.swordglowsblue.artifice.impl;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.ClientResourcePackContainer;
import net.minecraft.resource.ResourcePackContainer;

@Environment(EnvType.CLIENT)
public class ArtificeResourcePackContainer extends ClientResourcePackContainer {
    private final boolean optional;
    public boolean isOptional() { return this.optional; }

    public ArtificeResourcePackContainer(boolean optional, ResourcePackContainer wrapping) {
        super(
            wrapping.getName(),
            wrapping.canBeSorted(),
            wrapping::createResourcePack,
            wrapping.getDisplayName(),
            wrapping.getDescription(),
            wrapping.getCompatibility(),
            wrapping.getInitialPosition(),
            wrapping.isPositionFixed(),
            null
        );
        this.optional = optional;
    }
}
