package com.swordglowsblue.artifice.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.class_5369;
import net.minecraft.client.gui.screen.pack.PackListWidget;

@Mixin(PackListWidget.ResourcePackEntry.class)
public interface MixinResourcePackEntry {
    @Accessor("screen")
    class_5369.class_5371 getResourcePackInfo();
}
