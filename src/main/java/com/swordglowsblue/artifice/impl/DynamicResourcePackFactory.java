package com.swordglowsblue.artifice.impl;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.common.ClientOnly;
import com.swordglowsblue.artifice.common.ClientResourcePackProfileLike;
import com.swordglowsblue.artifice.common.ServerResourcePackProfileLike;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class DynamicResourcePackFactory<T extends ArtificeResourcePack.ResourcePackBuilder> implements ClientResourcePackProfileLike, ServerResourcePackProfileLike {

    private ResourceType type;
    private final Identifier identifier;
    private final Consumer<T> init;

    public DynamicResourcePackFactory(ResourceType type, Identifier identifier, Consumer<T> init) {
        this.type = type;
        this.identifier = identifier;
        this.init = init;
    }

    @Override
    public ClientOnly<ResourcePackProfile> toClientResourcePackProfile(ResourcePackProfile.Factory factory) {
        return new ArtificeResourcePackImpl(type, identifier, init).toClientResourcePackProfile(factory);
    }

    @Override
    public ResourcePackProfile toServerResourcePackProfile(ResourcePackProfile.Factory factory) {
        return new ArtificeResourcePackImpl(type, identifier, init).toServerResourcePackProfile(factory);
    }
}
