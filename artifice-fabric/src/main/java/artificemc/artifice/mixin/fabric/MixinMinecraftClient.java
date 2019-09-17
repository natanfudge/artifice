package artificemc.artifice.mixin.fabric;

import artificemc.artifice.api.fabric.ArtificeResourcePack;
import artificemc.artifice.api.fabric.ArtificeResourcePackContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.ClientResourcePackContainer;
import net.minecraft.resource.ResourcePackContainerManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Shadow @Final
    private ResourcePackContainerManager<ClientResourcePackContainer> resourcePackContainerManager;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void registerPackCreator(CallbackInfo cbi) {
        this.resourcePackContainerManager.addCreator(ArtificeResourcePack.Client.getCreator());
    }

    @Inject(method = "init", at = @At(value = "INVOKE",
            target = "net/minecraft/resource/ResourcePackContainerManager.callCreators()V"))
    private void enableNonOptional(CallbackInfo cbi) {
        this.resourcePackContainerManager.getDisabledContainers().forEach(c -> {
            if(c instanceof ArtificeResourcePackContainer && !((ArtificeResourcePackContainer)c).isOptional())
                this.resourcePackContainerManager.getEnabledContainers().add(c);
        });
    }
}
