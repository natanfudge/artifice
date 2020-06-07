package com.swordglowsblue.artifice.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.class_5369;
import net.minecraft.resource.ResourcePackProfile;

@Mixin(class_5369.class_5372.class)
public interface MixinExtendedResourcePackEntryInfo {
    @Accessor("field_25461")
    ResourcePackProfile getResourcePackProfile();
}
