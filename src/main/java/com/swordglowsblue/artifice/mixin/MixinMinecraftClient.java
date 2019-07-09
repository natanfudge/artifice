package com.swordglowsblue.artifice.mixin;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.virtualpack.ArtificeResourcePackContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.ClientResourcePackContainer;
import net.minecraft.resource.ResourcePackContainer;
import net.minecraft.resource.ResourcePackContainerManager;
import net.minecraft.resource.ResourcePackCreator;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Shadow private ResourcePackContainerManager<ClientResourcePackContainer> resourcePackContainerManager;

    @SuppressWarnings("unchecked")
    @Inject(method = "<init>", at = @At("RETURN"))
    private void registerPackCreator(CallbackInfo cbi) {
        this.resourcePackContainerManager.addCreator(new ResourcePackCreator() {
            public <T extends ResourcePackContainer> void registerContainer(Map<String, T> packs, ResourcePackContainer.Factory<T> factory) {
                for(Identifier id : Artifice.ASSETS.getIds())
                    packs.put(id.toString(), (T)Artifice.ASSETS.get(id).getAssetsContainer(factory));
            }
        });
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
