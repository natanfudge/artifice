package com.swordglowsblue.artifice.api;

import com.swordglowsblue.artifice.impl.ArtificeResourcePackImpl;
import com.swordglowsblue.artifice.impl.resource_types.ArtificeBlockStateResource;
import com.swordglowsblue.artifice.impl.resource_types.ArtificeModelResource;
import com.swordglowsblue.artifice.impl.util.IdUtils;
import com.swordglowsblue.artifice.impl.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public interface ArtificeResourcePack extends ResourcePack {
    ResourceType getType();

    @Environment(EnvType.CLIENT)
    static ArtificeResourcePack ofAssets(Consumer<ClientResourceRegistry> register) {
        return new ArtificeResourcePackImpl(ResourceType.CLIENT_RESOURCES, register::accept); }
    static ArtificeResourcePack ofData(Consumer<ServerResourceRegistry> register) {
        return new ArtificeResourcePackImpl(ResourceType.SERVER_DATA, register::accept); }

    @FunctionalInterface
    interface ResourceRegistry extends ClientResourceRegistry, ServerResourceRegistry {}

    @Environment(EnvType.CLIENT)
    interface ClientResourceRegistry {
        void add(Identifier id, ArtificeResource resource);

        default void addItemModel(Identifier id, Processor<ArtificeModelResource.ItemBuilder> settings) {
            ArtificeModelResource.ItemBuilder builder = new ArtificeModelResource.ItemBuilder();
            this.add(IdUtils.wrapPath("models/item/", id, ".json"), settings.process(builder).build());
        }

        default void addBlockModel(Identifier id, Processor<ArtificeModelResource.BlockBuilder> settings) {
            ArtificeModelResource.BlockBuilder builder = new ArtificeModelResource.BlockBuilder();
            this.add(IdUtils.wrapPath("models/block/", id, ".json"), settings.process(builder).build());
        }

        default void addBlockState(Identifier id, Processor<ArtificeBlockStateResource.Builder> settings) {
            ArtificeBlockStateResource.Builder builder = new ArtificeBlockStateResource.Builder();
            this.add(IdUtils.wrapPath("blockstates/", id, ".json"), settings.process(builder).build());
        }
    }

    interface ServerResourceRegistry {
        void add(Identifier id, ArtificeResource resource);
    }
}
