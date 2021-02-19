package com.swordglowsblue.artifice.mixin;

import com.swordglowsblue.artifice.api.virtualpack.ArtificeResourcePackContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

	/*@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;<init>(Lnet/minecraft/resource/ResourcePackProfile$class_5351;[Lnet/minecraft/resource/ResourcePackProvider;)V"), index = 1)
	private ResourcePackProvider[] appendArtificeAssets(ResourcePackProvider[] vanillaProviders) {
		return ArrayUtils.add(vanillaProviders, new ArtificeAssetsResourcePackProvider());
	}*/

	@Redirect(method = "<init>", at = @At(value = "INVOKE",
					target = "Lnet/minecraft/resource/ResourcePackManager;scanPacks()V"))
	private void enableNonOptional(ResourcePackManager resourcePackManager) {
		Collection<ResourcePackProfile> enabled = resourcePackManager.getEnabledProfiles();
		for (ResourcePackProfile profile : resourcePackManager.getProfiles()) {
			if (profile instanceof ArtificeResourcePackContainer && !((ArtificeResourcePackContainer) profile).isOptional()) {
				if (!enabled.contains(profile)) enabled.add(profile);
			}
		}

		resourcePackManager.scanPacks();
	}
}
