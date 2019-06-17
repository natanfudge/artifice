package com.swordglowsblue.artifice.api;

import com.swordglowsblue.artifice.impl.ArtificeResourcePackImpl;
import com.swordglowsblue.artifice.impl.builder.BlockStateBuilder;
import com.swordglowsblue.artifice.impl.builder.ModelBuilder;
import com.swordglowsblue.artifice.impl.builder.TranslationBuilder;
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
    boolean isOptional();

    @Environment(EnvType.CLIENT)
    static ArtificeResourcePack ofAssets(boolean optional, Consumer<ClientResourceRegistry> register) {
        return new ArtificeResourcePackImpl<>(ResourceType.CLIENT_RESOURCES, optional, register); }
    static ArtificeResourcePack ofData(Consumer<ServerResourceRegistry> register) {
        return new ArtificeResourcePackImpl<>(ResourceType.SERVER_DATA, true, register); }

    interface ResourceRegistry {
        void add(Identifier id, ArtificeResource resource);
    }

    @Environment(EnvType.CLIENT)
    interface ClientResourceRegistry extends ResourceRegistry {
        void addItemModel(Identifier id, Processor<ModelBuilder> f);
        void addBlockModel(Identifier id, Processor<ModelBuilder> f);
        void addBlockState(Identifier id, Processor<BlockStateBuilder> f);
        void addTranslations(Identifier id, Processor<TranslationBuilder> f);
        void addLanguage(LanguageDefinition def);
        void addLanguage(String code, String region, String name, boolean rtl);
    }

    interface ServerResourceRegistry extends ResourceRegistry {}
}
