package com.swordglowsblue.artifice.mixin;

import java.util.ArrayList;
import java.util.List;

import com.swordglowsblue.artifice.api.virtualpack.ArtificeResourcePackContainer;
import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.pack.AbstractPackScreen;
import net.minecraft.client.gui.screen.pack.PackListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.text.Text;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@Mixin(AbstractPackScreen.class)
public abstract class MixinResourcePackOptionsScreen {
    @Shadow
    private PackListWidget availablePackList;
    @Shadow
    private PackListWidget selectedPackList;
    private final List<PackListWidget.ResourcePackEntry> hidden = new ArrayList<>();

    private void tryHideEntry(PackListWidget.ResourcePackEntry entry) {
        ResourcePackOrganizer.Pack info = ((MixinResourcePackEntry) entry).getResourcePackInfo();
        if (info instanceof ResourcePackOrganizer.AbstractPack) {
            ResourcePackOrganizer.AbstractPack extendedInfo = (ResourcePackOrganizer.AbstractPack) info;
            ResourcePackProfile container = ((MixinExtendedResourcePackEntryInfo) extendedInfo).getResourcePackProfile();
            if (container instanceof ArtificeResourcePackContainer && !((ArtificeResourcePackContainer) container).isVisible()) {
                hidden.add(entry);
            }

        }
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void hideNoDisplayPacks(CallbackInfo cbi) {
        this.availablePackList.children().forEach(this::tryHideEntry);
        this.selectedPackList.children().forEach(this::tryHideEntry);
        this.availablePackList.children().removeAll(hidden);
        this.selectedPackList.children().removeAll(hidden);
    }

    @Redirect(method = "init", at = @At(value = "NEW", target = "(IIIILnet/minecraft/text/Text;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;)Lnet/minecraft/client/gui/widget/ButtonWidget;"/*, ordinal = 1*/))
    private ButtonWidget constructDoneButton(int x, int y, int width, int height, Text message, ButtonWidget.PressAction onPress) {
        return new ButtonWidget(x, y, width, height, message, (button) -> {
            this.selectedPackList.children().addAll(hidden);
            onPress.onPress(button);
        });
    }
}
