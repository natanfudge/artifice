package com.swordglowsblue.artifice.mixin;

import java.util.Arrays;

import com.google.common.collect.ImmutableSet;
import com.swordglowsblue.artifice.impl.ArtificeAssetsResourcePackProvider;
import com.swordglowsblue.artifice.impl.ArtificeDataResourcePackProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
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
    public <E> ImmutableSet<Object> appendArtificePacks(E[] elements) {
        Object addedPack;
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            addedPack = serverProvider;
        } else {
            try {
                Class clientBuiltinResourcePackProvider = Class.forName("net.minecraft.client.resource.ClientBuiltinResourcePackProvider");
                boolean isForClient = Arrays.stream(elements).anyMatch(element -> element.getClass().isAssignableFrom(clientBuiltinResourcePackProvider));
                addedPack = isForClient ? clientProvider : serverProvider;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                addedPack = serverProvider;
            }
        }
        return ImmutableSet.copyOf(ArrayUtils.add(elements, addedPack));
    }
}
