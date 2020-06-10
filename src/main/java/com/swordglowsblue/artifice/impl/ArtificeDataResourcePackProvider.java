package com.swordglowsblue.artifice.impl;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.common.ArtificeRegistry;

import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProvider;
import net.minecraft.util.Identifier;

public final class ArtificeDataResourcePackProvider implements ResourcePackProvider {
    @Override
    @SuppressWarnings( {"ConstantConditions", "unchecked"})
    public <T extends ResourcePackProfile> void register(Consumer<T> packs, ResourcePackProfile.Factory<T> factory) {
        for (Identifier id : ArtificeRegistry.DATA.getIds()) {
            packs.accept((T) ArtificeRegistry.DATA.get(id).toServerResourcePackProfile(factory));
        }
    }

}
