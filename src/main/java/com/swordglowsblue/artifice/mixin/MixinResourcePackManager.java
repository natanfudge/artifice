package com.swordglowsblue.artifice.mixin;

import java.util.Arrays;

import com.google.common.collect.ImmutableSet;
import com.swordglowsblue.artifice.impl.ArtificeAssetsResourcePackProvider;
import com.swordglowsblue.artifice.impl.ArtificeDataResourcePackProvider;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.resource.ClientBuiltinResourcePackProvider;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProvider;

@Mixin(ResourcePackManager.class)
public class MixinResourcePackManager {
    private static final ArtificeAssetsResourcePackProvider clientProvider = new ArtificeAssetsResourcePackProvider();
    private static final ArtificeDataResourcePackProvider serverProvider = new ArtificeDataResourcePackProvider();

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableSet;copyOf([Ljava/lang/Object;)Lcom/google/common/collect/ImmutableSet;"))
    public <E> ImmutableSet<Object> appendArtificePacks(E[] elements) {
        boolean isForClient = Arrays.stream(elements).anyMatch(element -> element instanceof ClientBuiltinResourcePackProvider);
        Object addedPack = isForClient ? clientProvider : serverProvider;
        return ImmutableSet.copyOf(ArrayUtils.add(elements, addedPack));
    }
}
