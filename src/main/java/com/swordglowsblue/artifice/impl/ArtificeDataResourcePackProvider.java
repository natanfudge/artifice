package com.swordglowsblue.artifice.impl;

import java.util.Map;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.common.ArtificeRegistry;

import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProvider;
import net.minecraft.util.Identifier;

public final class ArtificeDataResourcePackProvider implements ResourcePackProvider {
    @Override
    @SuppressWarnings( {"ConstantConditions", "unchecked", "deprecation"})
    public <T extends ResourcePackProfile> void register(Map<String, T> packs, ResourcePackProfile.Factory<T> factory) {
        //TODO: remove
        for (Identifier id : Artifice.DATA.getIds()) {
            packs.put(id.toString(), (T) Artifice.DATA.get(id).getDataContainer(factory));
        }
        for (Identifier id : ArtificeRegistry.DATA.getIds()) {
            packs.put(id.toString(), (T) ArtificeRegistry.DATA.get(id).toServerResourcePackProfile(factory));
        }
    }

}
