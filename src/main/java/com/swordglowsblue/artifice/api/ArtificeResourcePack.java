package com.swordglowsblue.artifice.api;

import com.swordglowsblue.artifice.impl.ArtificeResourcePackImpl;
import com.swordglowsblue.artifice.impl.ArtificeResourcePackImpl.ResourceRegistry;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;

import java.util.function.Consumer;

public interface ArtificeResourcePack extends ResourcePack {
    ResourceType getType();

    static ArtificeResourcePack ofAssets(Consumer<ResourceRegistry> registerResources) {
        return new ArtificeResourcePackImpl(ResourceType.CLIENT_RESOURCES, registerResources); }
    static ArtificeResourcePack ofData(Consumer<ResourceRegistry> registerResources) {
        return new ArtificeResourcePackImpl(ResourceType.SERVER_DATA, registerResources); }
}
