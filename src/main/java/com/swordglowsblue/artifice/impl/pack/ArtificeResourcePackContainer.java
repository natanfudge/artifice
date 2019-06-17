package com.swordglowsblue.artifice.impl.pack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.ClientResourcePackContainer;
import net.minecraft.resource.ResourcePackContainer;

@Environment(EnvType.CLIENT)
public class ArtificeResourcePackContainer extends ClientResourcePackContainer {
    private final boolean optional;
    private final boolean visible;
    public boolean isOptional() { return this.optional; }
    public boolean isVisible() { return this.visible; }

    public ArtificeResourcePackContainer(boolean optional, boolean visible, ResourcePackContainer wrapping) {
        super(
            wrapping.getName(),
            !optional,
            wrapping::createResourcePack,
            wrapping.getDisplayName(),
            wrapping.getDescription(),
            wrapping.getCompatibility(),
            wrapping.getInitialPosition(),
            wrapping.isPositionFixed(),
            null
        );

        this.optional = optional;
        this.visible = visible;
    }
}
