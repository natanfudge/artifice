package com.swordglowsblue.artifice.mixin;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProvider;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

import com.swordglowsblue.artifice.impl.ArtificeDataResourcePackProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {
    @Final @Shadow private ResourcePackManager<ResourcePackProfile> dataPackManager;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void registerPackCreator(CallbackInfo cbi) {
        this.dataPackManager.registerProvider(new ArtificeDataResourcePackProvider());
    }
}
