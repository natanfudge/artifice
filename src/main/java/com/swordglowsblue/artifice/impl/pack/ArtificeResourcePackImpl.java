package com.swordglowsblue.artifice.impl.pack;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResource;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.builder.assets.*;
import com.swordglowsblue.artifice.api.builder.data.AdvancementBuilder;
import com.swordglowsblue.artifice.api.builder.data.LootTableBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.impl.util.IdUtils;
import com.swordglowsblue.artifice.impl.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.EnvironmentInterface;
import net.minecraft.SharedConstants;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ArtificeResourcePackImpl implements ArtificeResourcePack {
    private final ResourceType type;
    private final Set<String> namespaces = new HashSet<>();
    private final Map<Identifier, ArtificeResource> resources = new HashMap<>();
    private final Set<LanguageDefinition> languages = new HashSet<>();

    private String description;
    private String displayName;
    private boolean optional;
    private boolean visible;

    public <T extends ResourceRegistry> ArtificeResourcePackImpl(ResourceType type, Consumer<T> registerResources) {
        this.type = type;
        registerResources.accept((T)new ResourceRegistryImpl());
    }

    @EnvironmentInterface(value = EnvType.CLIENT, itf = ClientResourceRegistry.class)
    private final class ResourceRegistryImpl implements ClientResourceRegistry, ServerResourceRegistry {
        private ResourceRegistryImpl() {}

        public void setDisplayName(String name) { ArtificeResourcePackImpl.this.displayName = name; }
        public void setDescription(String desc) { ArtificeResourcePackImpl.this.description = desc; }
        public void setVisible() { ArtificeResourcePackImpl.this.visible = true; }
        public void setOptional() {
            ArtificeResourcePackImpl.this.optional = true;
            ArtificeResourcePackImpl.this.visible = true;
        }

        public void add(Identifier id, ArtificeResource resource) {
            ArtificeResourcePackImpl.this.resources.put(id, resource);
            ArtificeResourcePackImpl.this.namespaces.add(id.getNamespace());
        }

        public void addItemModel(Identifier id, Processor<ModelBuilder> f) {
            this.addJson("models/item/", id, f, ModelBuilder::new); }
        public void addBlockModel(Identifier id, Processor<ModelBuilder> f) {
            this.addJson("models/block/", id, f, ModelBuilder::new); }
        public void addBlockState(Identifier id, Processor<BlockStateBuilder> f) {
            this.addJson("blockstates/", id, f, BlockStateBuilder::new); }
        public void addTranslations(Identifier id, Processor<TranslationBuilder> f) {
            this.addJson("lang/", id, f, TranslationBuilder::new); }
        public void addParticle(Identifier id, Processor<ParticleBuilder> f) {
            this.addJson("particles/", id, f, ParticleBuilder::new); }
        public void addItemAnimation(Identifier id, Processor<AnimationBuilder> f) {
            this.addMcmeta("textures/item/", id, f, AnimationBuilder::new); }
        public void addBlockAnimation(Identifier id, Processor<AnimationBuilder> f) {
            this.addMcmeta("textures/block/", id, f, AnimationBuilder::new); }

        public void addLanguage(LanguageDefinition def) { ArtificeResourcePackImpl.this.languages.add(def); }
        public void addLanguage(String code, String region, String name, boolean rtl) {
            this.addLanguage(new LanguageDefinition(code, region, name, rtl));
        }

        public void addAdvancement(Identifier id, Processor<AdvancementBuilder> f) {
            this.addJson("advancements/", id, f, AdvancementBuilder::new); }
        public void addLootTable(Identifier id, Processor<LootTableBuilder> f) {
            this.addJson("loot_tables/", id, f, LootTableBuilder::new); }

        private <T extends TypedJsonBuilder<? extends JsonResource>> void addJson(String path, Identifier id, Processor<T> f, Supplier<T> ctor) {
            this.add(IdUtils.wrapPath(path, id, ".json"), f.process(ctor.get()).build()); }
        private <T extends TypedJsonBuilder<? extends JsonResource>> void addMcmeta(String path, Identifier id, Processor<T> f, Supplier<T> ctor) {
            this.add(IdUtils.wrapPath(path, id, ".mcmeta"), f.process(ctor.get()).build()); }
    }

    public InputStream openRoot(String fname) throws IOException { return open(this.type, new Identifier(fname)); }
    public InputStream open(ResourceType type, Identifier id) throws IOException {
        if(!contains(type, id)) throw new FileNotFoundException(id.getPath());
        return this.resources.get(id).toInputStream();
    }

    public Collection<Identifier> findResources(ResourceType type, String rootFolder, int max, Predicate<String> filter) {
        if(type != this.type) return new HashSet<>();
        Set<Identifier> keys = Sets.newHashSet(this.resources.keySet());
        keys.removeIf(id -> !id.getPath().split("[\\/]")[0].equals(rootFolder));
        keys.removeIf(id -> !filter.test(id.getPath()));
        return keys;
    }

    public boolean contains(ResourceType type, Identifier id) {
        return type == this.type && this.resources.containsKey(id);
    }

    public <T> T parseMetadata(ResourceMetadataReader<T> reader) {
        JsonObject packMeta = new JsonObject();
        packMeta.addProperty("pack_format", SharedConstants.getGameVersion().getPackVersion());
        packMeta.addProperty("description", description != null ? description : "In-memory resource pack created with Artifice");

        JsonObject languageMeta = new JsonObject();
        for(LanguageDefinition def : languages) {
            JsonObject language = new JsonObject();
            language.addProperty("name", def.getName());
            language.addProperty("region", def.getRegion());
            language.addProperty("bidirectional", def.isRightToLeft());
            languageMeta.add(def.getCode(), language);
        }

        JsonObject meta = new JsonObject();
        meta.add("pack", packMeta);
        meta.add("language", languageMeta);

        return meta.has(reader.getKey())
            ? reader.fromJson(meta.getAsJsonObject(reader.getKey()))
            : null;
    }

    public Set<String> getNamespaces(ResourceType type) { return new HashSet<>(this.namespaces); }
    public ResourceType getType() { return this.type; }
    public boolean isOptional() { return this.optional; }
    public boolean isVisible() { return this.visible; }
    public void close() {}

    public String getName() {
        if(displayName != null) return displayName;
        switch(this.type) {
            case CLIENT_RESOURCES: return displayName = Artifice.ASSETS.getId(this).toString();
            case SERVER_DATA: return displayName = Artifice.DATA.getId(this).toString();
            default: return displayName;
        }
    }
}
