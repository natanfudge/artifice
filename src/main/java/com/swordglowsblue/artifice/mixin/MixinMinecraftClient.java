package com.swordglowsblue.artifice.mixin;

import java.util.Collection;

import com.swordglowsblue.artifice.api.virtualpack.ArtificeResourcePackContainer;
import com.swordglowsblue.artifice.impl.ArtificeAssetsResourcePackProvider;
import com.swordglowsblue.artifice.impl.ArtificeDataResourcePackProvider;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.ClientResourcePackProfile;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProvider;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

//	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;<init>(Lnet/minecraft/resource/ResourcePackProfile$class_5351;[Lnet/minecraft/resource/ResourcePackProvider;)V"), index = 1)
//	private ResourcePackProvider[] appendArtificeAssets(ResourcePackProvider[] vanillaProviders) {
//		return ArrayUtils.add(vanillaProviders, new ArtificeAssetsResourcePackProvider());
//	}

	@Redirect(method = "<init>", at = @At(value = "INVOKE",
					target = "Lnet/minecraft/resource/ResourcePackManager;scanPacks()V"))
	private void enableNonOptional(ResourcePackManager<ClientResourcePackProfile> resourcePackManager) {
		Collection<ClientResourcePackProfile> enabled = resourcePackManager.getEnabledProfiles();
		for (ClientResourcePackProfile profile : resourcePackManager.getProfiles()) {
			if (profile instanceof ArtificeResourcePackContainer && !((ArtificeResourcePackContainer) profile).isOptional()) {
				if (!enabled.contains(profile)) enabled.add(profile);
			}
		}

		resourcePackManager.scanPacks();
	}
}
