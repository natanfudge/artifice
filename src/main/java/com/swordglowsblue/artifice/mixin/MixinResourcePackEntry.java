package com.swordglowsblue.artifice.mixin;

import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screen.pack.PackListWidget;

@Mixin(PackListWidget.ResourcePackEntry.class)
public interface MixinResourcePackEntry {
    @Accessor("pack")
    ResourcePackOrganizer.Pack getResourcePackInfo();
}
