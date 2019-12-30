package com.swordglowsblue.artifice.mixin;

import com.swordglowsblue.artifice.api.virtualpack.ArtificeResourcePackContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.resourcepack.AvailableResourcePackListWidget;
import net.minecraft.client.gui.screen.resourcepack.ResourcePackListWidget;
import net.minecraft.client.gui.screen.resourcepack.ResourcePackOptionsScreen;
import net.minecraft.client.gui.screen.resourcepack.SelectedResourcePackListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.ClientResourcePackProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
@Mixin(ResourcePackOptionsScreen.class)
public abstract class MixinResourcePackOptionsScreen {
    @Shadow private AvailableResourcePackListWidget availablePacks;
    @Shadow private SelectedResourcePackListWidget enabledPacks;
    private final List<ResourcePackListWidget.ResourcePackEntry> hidden = new ArrayList<>();

    private void tryHideEntry(ResourcePackListWidget.ResourcePackEntry entry) {
        ClientResourcePackProfile container = entry.getPack();
        if(container instanceof ArtificeResourcePackContainer && !((ArtificeResourcePackContainer)container).isVisible()) hidden.add(entry);
    };

    @Inject(method = "init", at = @At("RETURN"))
    private void hideNoDisplayPacks(CallbackInfo cbi) {
        this.availablePacks.children().forEach(this::tryHideEntry);
        this.enabledPacks.children().forEach(this::tryHideEntry);
        this.availablePacks.children().removeAll(hidden);
        this.enabledPacks.children().removeAll(hidden);
    }

    @Redirect(method = "init", at = @At(value = "NEW", target = "net/minecraft/client/gui/widget/ButtonWidget", ordinal = 1))
    private ButtonWidget constructDoneButton(int x, int y, int w, int h, String text, ButtonWidget.PressAction onClick) {
        return new ButtonWidget(x, y, w, h, text, (button) -> {
           this.enabledPacks.children().addAll(hidden);
           onClick.onPress(button);
        });
    }
}
