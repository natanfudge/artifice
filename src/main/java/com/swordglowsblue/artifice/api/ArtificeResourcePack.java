package com.swordglowsblue.artifice.api;

import com.swordglowsblue.artifice.impl.ArtificeResourcePackImpl;
import com.swordglowsblue.artifice.impl.builder.BlockStateBuilder;
import com.swordglowsblue.artifice.impl.builder.ModelBuilder;
import com.swordglowsblue.artifice.impl.builder.TranslationBuilder;
import com.swordglowsblue.artifice.impl.resource.*;
import com.swordglowsblue.artifice.impl.util.IdUtils;
import com.swordglowsblue.artifice.impl.util.JsonBuilder;
import com.swordglowsblue.artifice.impl.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ArtificeResourcePack extends ResourcePack {
    ResourceType getType();
    boolean isOptional();

    @Environment(EnvType.CLIENT)
    static ArtificeResourcePack ofAssets(boolean optional, Consumer<ClientResourceRegistry> register) {
        return new ArtificeResourcePackImpl(ResourceType.CLIENT_RESOURCES, optional, register::accept); }
    static ArtificeResourcePack ofData(Consumer<ServerResourceRegistry> register) {
        return new ArtificeResourcePackImpl(ResourceType.SERVER_DATA, true, register::accept); }

    @FunctionalInterface
    interface ResourceRegistry extends ClientResourceRegistry, ServerResourceRegistry {}

    @Environment(EnvType.CLIENT)
    interface ClientResourceRegistry {
        void add(Identifier id, ArtificeResource resource);

        default void addItemModel(Identifier id, Processor<ModelBuilder> f) {
            this.addJson("models/item/", id, f, ModelBuilder::new); }
        default void addBlockModel(Identifier id, Processor<ModelBuilder> f) {
            this.addJson("models/block/", id, f, ModelBuilder::new); }
        default void addBlockState(Identifier id, Processor<BlockStateBuilder> f) {
            this.addJson("blockstates/", id, f, BlockStateBuilder::new); }
        default void addTranslations(Identifier id, Processor<TranslationBuilder> f) {
            this.addJson("lang/", id, f, TranslationBuilder::new); }

        default void addLanguage(LanguageDefinition def) { this.add(null, new LanguageResource(def)); }
        default void addLanguage(String code, String region, String name, boolean rtl) {
            this.addLanguage(new LanguageDefinition(code, region, name, rtl));
        }

        default <T extends JsonBuilder<? extends JsonResource>> void addJson(String path, Identifier id, Processor<T> f, Supplier<T> ctor) {
            this.add(IdUtils.wrapPath(path, id, ".json"), f.process(ctor.get()).build());
        }
    }

    interface ServerResourceRegistry {
        void add(Identifier id, ArtificeResource resource);
    }
}
