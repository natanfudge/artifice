package com.swordglowsblue.artifice.mixin;

import java.util.Arrays;

import com.google.common.collect.ImmutableSet;
import com.swordglowsblue.artifice.impl.ArtificeAssetsResourcePackProvider;
import com.swordglowsblue.artifice.impl.ArtificeDataResourcePackProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.resource.ClientBuiltinResourcePackProvider;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProvider;

@Mixin(ResourcePackManager.class)
public class MixinResourcePackManager {
    private static final ArtificeAssetsResourcePackProvider clientProvider = new ArtificeAssetsResourcePackProvider();
    private static final ArtificeDataResourcePackProvider serverProvider = new ArtificeDataResourcePackProvider();

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableSet;copyOf([Ljava/lang/Object;)Lcom/google/common/collect/ImmutableSet;"))
    private <E> ImmutableSet<Object> appendArtificePacks(E[] elements) {
        Object addedPack;
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            addedPack = serverProvider;
        } else {
            boolean isForClient = Arrays.stream(elements).anyMatch(element -> element instanceof ClientBuiltinResourcePackProvider);
            addedPack = isForClient ? clientProvider : serverProvider;
        }
        return ImmutableSet.copyOf(ArrayUtils.add(elements, addedPack));
    }
}
