package com.swordglowsblue.artifice.api;

import com.swordglowsblue.artifice.impl.ArtificeResourcePackImpl;
import com.swordglowsblue.artifice.impl.resource.BlockStateResource;
import com.swordglowsblue.artifice.impl.resource.ModelResource;
import com.swordglowsblue.artifice.impl.resource.TranslationResource;
import com.swordglowsblue.artifice.impl.util.IdUtils;
import com.swordglowsblue.artifice.impl.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.LanguageDefinition;
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

        default void addItemModel(Identifier id, Processor<ModelResource.Builder.Item> settings) {
            ModelResource.Builder.Item builder = new ModelResource.Builder.Item();
            this.add(IdUtils.wrapPath("models/item/", id, ".json"), settings.process(builder).build());
        }

        default void addBlockModel(Identifier id, Processor<ModelResource.Builder.Block> settings) {
            ModelResource.Builder.Block builder = new ModelResource.Builder.Block();
            this.add(IdUtils.wrapPath("models/block/", id, ".json"), settings.process(builder).build());
        }

        default void addBlockState(Identifier id, Processor<BlockStateResource.Builder> settings) {
            BlockStateResource.Builder builder = new BlockStateResource.Builder();
            this.add(IdUtils.wrapPath("blockstates/", id, ".json"), settings.process(builder).build());
        }

        default void addTranslations(Identifier id, Processor<TranslationResource.Builder> settings) {
            TranslationResource.Builder builder = new TranslationResource.Builder(id.getPath());
            this.add(IdUtils.wrapPath("lang/", id, ".json"), settings.process(builder).build());
        }

        default void addLanguage(Identifier id, LanguageDefinition def, Processor<TranslationResource.Builder> settings) {
            TranslationResource.Builder builder = new TranslationResource.Builder(def);
            this.add(IdUtils.wrapPath("lang/", id, ".json"), settings.process(builder).build());
        }
    }

    interface ServerResourceRegistry {
        void add(Identifier id, ArtificeResource resource);
    }
}
