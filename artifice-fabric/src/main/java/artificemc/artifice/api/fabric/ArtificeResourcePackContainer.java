package artificemc.artifice.api.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.ClientResourcePackContainer;
import net.minecraft.resource.ResourcePackContainer;

/** A wrapper around {@link ClientResourcePackContainer} exposing optionality/visibility.
 *  @see ResourcePackBuilder.Client#setOptional
 *  @see ResourcePackBuilder.Client#setVisible */
@Environment(EnvType.CLIENT)
public class ArtificeResourcePackContainer extends ClientResourcePackContainer {
    private final boolean optional;
    private final boolean visible;
    /** @return Whether this pack is optional. */
    public boolean isOptional() { return this.optional; }
    /** @return Whether this pack is visible. */
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
