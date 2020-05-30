package com.swordglowsblue.artifice.impl;

import java.util.Map;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.common.ArtificeRegistry;

import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProvider;
import net.minecraft.util.Identifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;


public final class ArtificeAssetsResourcePackProvider implements ResourcePackProvider {
    @Override
    @SuppressWarnings( {"unchecked", "ConstantConditions"})
    public <T extends ResourcePackProfile> void register(Map<String, T> packs, ResourcePackProfile.Factory<T> factory) {
        for (Identifier id : ArtificeRegistry.ASSETS.getIds()) {
            packs.put(id.toString(), (T) ArtificeRegistry.ASSETS.get(id).toClientResourcePackProfile(factory).get());
        }
    }
}

