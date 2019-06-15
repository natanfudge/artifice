package com.swordglowsblue.artifice.impl;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResource;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.impl.resource.TranslationResource;
import net.minecraft.SharedConstants;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import org.apache.commons.io.input.NullInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ArtificeResourcePackImpl implements ArtificeResourcePack {
    private final Set<String> namespaces;
    private final ResourceType type;
    private final Map<Identifier, ArtificeResource> resources = new HashMap<>();

    public ArtificeResourcePackImpl(ResourceType type, Consumer<ResourceRegistry> registerResources) {
        this.type = type;
        this.namespaces = new HashSet<>();

        registerResources.accept((id, resource) -> {
            this.resources.put(id, resource);
            this.namespaces.add(id.getNamespace());
        });
    }

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
        packMeta.addProperty("description", "In-memory resource pack via Artifice");

        JsonObject languageMeta = new JsonObject();
        for(ArtificeResource resource : resources.values()) {
            if(!(resource instanceof TranslationResource)) continue;

            JsonObject language = new JsonObject();
            LanguageDefinition def = ((TranslationResource)resource).getLanguage();

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

    public InputStream openRoot(String filename) { return new NullInputStream(0); }
    public Set<String> getNamespaces(ResourceType type) { return new HashSet<>(this.namespaces); }
    public ResourceType getType() { return this.type; }
    public void close() {}

    private String name;
    public String getName() {
        if(name != null) return name;
        switch(this.type) {
            case CLIENT_RESOURCES: return name = Artifice.ASSETS.getId(this).toString();
            case SERVER_DATA: return name = Artifice.DATA.getId(this).toString();
            default: return name = "In-memory resource pack via Artifice";
        }
    }
}
