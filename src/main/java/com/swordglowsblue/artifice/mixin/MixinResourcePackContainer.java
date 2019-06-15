package com.swordglowsblue.artifice.mixin;

import com.swordglowsblue.artifice.impl.ArtificeResourcePackContainer;
import net.minecraft.resource.ResourcePackContainer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ResourcePackContainer.class)
public class MixinResourcePackContainer implements ArtificeResourcePackContainer {
    private boolean optional = true;
    public boolean artifice_isOptional() { return optional; }
    public void artifice_setOptional(boolean b) { this.optional = b; }
}
