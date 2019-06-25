package com.swordglowsblue.artifice.mixin;

import com.swordglowsblue.artifice.impl.ArtificeResourcePackContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.resourcepack.AvailableResourcePackListWidget;
import net.minecraft.client.gui.screen.resourcepack.ResourcePackListWidget;
import net.minecraft.client.gui.screen.resourcepack.ResourcePackOptionsScreen;
import net.minecraft.client.gui.screen.resourcepack.SelectedResourcePackListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.ClientResourcePackContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(ResourcePackOptionsScreen.class)
public abstract class MixinResourcePackOptionsScreen {
    @Shadow private AvailableResourcePackListWidget availableList;
    @Shadow private SelectedResourcePackListWidget selectedList;
    private final List<ResourcePackListWidget.ResourcePackEntry> hidden = new ArrayList<>();

    @Inject(method = "init", at = @At("RETURN"))
    private void hideNoDisplayPacks(CallbackInfo cbi) {
        List<ResourcePackListWidget.ResourcePackEntry> toRemove = new ArrayList<>();

        this.availableList.children().forEach(entry -> {
            ClientResourcePackContainer container = entry.getPackContainer();
            if(container instanceof ArtificeResourcePackContainer && !((ArtificeResourcePackContainer)container).isVisible()) toRemove.add(entry);
        });

        this.availableList.children().removeAll(toRemove);
        this.hidden.addAll(toRemove);
        toRemove.clear();

        this.selectedList.children().forEach(entry -> {
            ClientResourcePackContainer container = entry.getPackContainer();
            if(container instanceof ArtificeResourcePackContainer && !((ArtificeResourcePackContainer)container).isVisible()) toRemove.add(entry);
        });

        this.selectedList.children().removeAll(toRemove);
        this.hidden.addAll(toRemove);
    }

    @Redirect(method = "init", at = @At(value = "NEW", target = "net/minecraft/client/gui/widget/ButtonWidget", ordinal = 1))
    public ButtonWidget constructDoneButton(int x, int y, int w, int h, String text, ButtonWidget.PressAction onClick) {
        return new ButtonWidget(x, y, w, h, text, (button) -> {
           selectedList.children().addAll(hidden);
           onClick.onPress(button);
        });
    }
}
