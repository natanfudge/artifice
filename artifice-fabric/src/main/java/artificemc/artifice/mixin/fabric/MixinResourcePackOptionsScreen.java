package artificemc.artifice.mixin.fabric;

import artificemc.artifice.api.fabric.ArtificeResourcePackContainer;
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

    private void tryHideEntry(ResourcePackListWidget.ResourcePackEntry entry) {
        ClientResourcePackContainer container = entry.getPackContainer();
        if(container instanceof ArtificeResourcePackContainer &&
           !((ArtificeResourcePackContainer)container).isVisible()) hidden.add(entry);
    };

    @Inject(method = "init", at = @At("RETURN"))
    private void hideNoDisplayPacks(CallbackInfo cbi) {
        this.availableList.children().forEach(this::tryHideEntry);
        this.selectedList.children().forEach(this::tryHideEntry);
        this.availableList.children().removeAll(hidden);
        this.selectedList.children().removeAll(hidden);
    }

    @Redirect(method = "init", at = @At(value = "NEW", target = "net/minecraft/client/gui/widget/ButtonWidget", ordinal = 1))
    public ButtonWidget constructDoneButton(int x, int y, int w, int h, String text, ButtonWidget.PressAction onClick) {
        return new ButtonWidget(x, y, w, h, text, (button) -> {
           this.selectedList.children().addAll(hidden);
           onClick.onPress(button);
        });
    }
}
