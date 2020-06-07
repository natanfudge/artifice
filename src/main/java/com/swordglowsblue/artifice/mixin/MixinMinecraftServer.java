//package com.swordglowsblue.artifice.mixin;
//
//import com.swordglowsblue.artifice.impl.ArtificeDataResourcePackProvider;
//import org.apache.commons.lang3.ArrayUtils;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.ModifyArg;
//
//import net.minecraft.resource.ResourcePackManager;
//import net.minecraft.resource.ResourcePackProfile;
//import net.minecraft.resource.ResourcePackProvider;
//import net.minecraft.server.MinecraftServer;
//
//@Mixin(MinecraftServer.class)
//public abstract class MixinMinecraftServer {
//    @ModifyArg(method = "method_29438", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;<init>(Lnet/minecraft/resource/ResourcePackProfile$Factory;[Lnet/minecraft/resource/ResourcePackProvider;)V"), index = 1)
//    private static ResourcePackProvider[] appendArtificeDataPacks(ResourcePackProvider[] vanillaProviders) {
//        return ArrayUtils.add(vanillaProviders, new ArtificeDataResourcePackProvider());
//    }
//}
