package com.swordglowsblue.artifice.mixin;

import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.resource.ResourcePackProfile;

@Mixin(ResourcePackOrganizer.AbstractPack.class)
public interface MixinExtendedResourcePackEntryInfo {
    @Accessor("profile")
    ResourcePackProfile getResourcePackProfile();
}
