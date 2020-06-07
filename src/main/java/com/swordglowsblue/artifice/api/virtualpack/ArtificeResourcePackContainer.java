package com.swordglowsblue.artifice.api.virtualpack;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.impl.ArtificeResourcePackImpl;

import net.minecraft.client.resource.ClientResourcePackProfile;
import net.minecraft.resource.ResourcePackProfile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * A wrapper around {@link ClientResourcePackProfile} exposing optionality/visibility.
 *
 * @see ArtificeResourcePack.ClientResourcePackBuilder#setOptional
 * @see ArtificeResourcePack.ClientResourcePackBuilder#setVisible
 */
@Environment(EnvType.CLIENT)
public class ArtificeResourcePackContainer extends ClientResourcePackProfile {
    private final boolean optional;
    private final boolean visible;

    /**
     * @return Whether this pack is optional.
     */
    public boolean isOptional() {
        return this.optional;
    }

    /**
     * @return Whether this pack is visible.
     */
    public boolean isVisible() {
        return this.visible;
    }

    public ArtificeResourcePackContainer(boolean optional, boolean visible, ResourcePackProfile wrapping) {
        super(
                        wrapping.getName(),
                        !optional,
                        wrapping::createResourcePack,
                        wrapping.getDisplayName(),
                        wrapping.getDescription(),
                        wrapping.getCompatibility(),
                        wrapping.getInitialPosition(),
                        wrapping.isPinned(),
                        ArtificeResourcePackImpl.ARTIFICE_RESOURCE_PACK_SOURCE,
                        null
        );

        this.optional = optional;
        this.visible = visible;
    }
}
