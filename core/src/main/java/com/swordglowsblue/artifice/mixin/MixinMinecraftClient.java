package com.swordglowsblue.artifice.mixin;

import com.swordglowsblue.artifice.api.virtualpack.ArtificeResourcePackContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.ClientResourcePackProfile;
import net.minecraft.resource.ResourcePackManager;

import com.swordglowsblue.artifice.impl.ArtificeAssetsResourcePackProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Final @Shadow private ResourcePackManager<ClientResourcePackProfile> resourcePackManager;

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;currentThread()Ljava/lang/Thread;"))
    private Thread registerPackCreator() {
        this.resourcePackManager.registerProvider(new ArtificeAssetsResourcePackProvider());
        return Thread.currentThread();
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/resource/ResourcePackManager;scanPacks()V"))
    private void enableNonOptional(ResourcePackManager resourcePackManager) {
        this.resourcePackManager.getDisabledProfiles().forEach(c -> {
            if(c instanceof ArtificeResourcePackContainer && !((ArtificeResourcePackContainer)c).isOptional())
                this.resourcePackManager.getEnabledProfiles().add(c);
        });

        resourcePackManager.scanPacks();
    }
}
