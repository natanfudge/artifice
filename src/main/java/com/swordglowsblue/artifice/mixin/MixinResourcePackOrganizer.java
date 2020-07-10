package com.swordglowsblue.artifice.mixin;

import java.util.List;
import java.util.stream.Stream;

import com.swordglowsblue.artifice.api.virtualpack.ArtificeResourcePackContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer;
import net.minecraft.resource.ResourcePackProfile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Mixin(ResourcePackOrganizer.class)
@Environment(EnvType.CLIENT)
public class MixinResourcePackOrganizer {

	@Redirect(method = "getDisabledPacks", at = @At(value = "INVOKE", target = "Ljava/util/List;stream()Ljava/util/stream/Stream;"))
	private Stream<ResourcePackProfile> hideNoDisplayPacksFromDisabled(List<ResourcePackProfile> list) {
		return list.stream().filter(this::isVisible);
	}

	@Redirect(method = "getEnabledPacks", at = @At(value = "INVOKE", target = "Ljava/util/List;stream()Ljava/util/stream/Stream;"))
	private Stream<ResourcePackProfile> hideNoDisplayPacksFromEnabled(List<ResourcePackProfile> list) {
		return list.stream().filter(this::isVisible);
	}

	private boolean isVisible(ResourcePackProfile profile) {
		return !(profile instanceof ArtificeResourcePackContainer)
										|| ((ArtificeResourcePackContainer) profile).isVisible();
	}
}


