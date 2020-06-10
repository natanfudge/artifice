package com.swordglowsblue.artifice.impl;

import java.util.function.Consumer;

import com.swordglowsblue.artifice.common.ArtificeRegistry;

import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProvider;
import net.minecraft.util.Identifier;


public final class ArtificeAssetsResourcePackProvider implements ResourcePackProvider {
    @Override
    public <T extends ResourcePackProfile> void register(Consumer<T> consumer, ResourcePackProfile.Factory<T> factory) {
        for (Identifier id : ArtificeRegistry.ASSETS.getIds()) {
            consumer.accept((T) ArtificeRegistry.ASSETS.get(id).toClientResourcePackProfile(factory).get());
        }
    }
}

