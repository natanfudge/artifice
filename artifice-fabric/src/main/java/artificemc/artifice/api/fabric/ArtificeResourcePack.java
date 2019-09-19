package artificemc.artifice.api.fabric;

import artificemc.artifice.api.builders.JsonObjectBuilder;
import artificemc.artifice.api.builders.TypedJsonBuilder;
import artificemc.artifice.api.builders.client.*;
import artificemc.artifice.api.builders.server.*;
import artificemc.artifice.api.core.resource.JsonResource;
import artificemc.artifice.api.core.resource.Resource;
import artificemc.artifice.api.core.resource.container.ResourceContainer;
import artificemc.artifice.api.core.resource.container.ResourceNotFoundException;
import artificemc.artifice.api.core.util.Processor;
import artificemc.artifice.impl.core.util.IdUtils;
import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.EnvironmentInterface;
import net.minecraft.SharedConstants;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackContainer;
import net.minecraft.resource.ResourcePackCreator;
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
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class ArtificeResourcePack implements ResourceContainer, ResourcePack {
    protected final String id;
    protected final JsonResource metadata;
    protected final Set<String> namespaces = new HashSet<>();
    protected final Map<String, Resource> resources = new HashMap<>();

    protected final List<LanguageDefinition> languages = new ArrayList<>();
    protected String description;
    protected String displayName;
    protected boolean optional;
    protected boolean visible;

    @SuppressWarnings("unchecked")
    private <T extends ResourcePackBuilder> ArtificeResourcePack(String id, Consumer<T> register) {
        IdUtils.validateIdentifier(id);
        this.id = id;
        register.accept((T)new ArtificeResourcePackBuilder());

        JsonObject packMeta = new JsonObjectBuilder()
            .set("pack_format", SharedConstants.getGameVersion().getPackVersion())
            .set("description", description != null ? description : "In-memory resource pack created with Artifice")
            .build();

        JsonObject languageMeta = new JsonObject();
        for(LanguageDefinition def : languages)
            languageMeta.add(def.getCode(), new JsonObjectBuilder()
                .set("name", def.getName())
                .set("region", def.getRegion())
                .set("bidirectional", def.isRightToLeft())
                .build());

        JsonObjectBuilder builder = new JsonObjectBuilder();
        builder.set("pack", packMeta);
        if(languages.size() > 0) builder.set("language", languageMeta);
        this.metadata = new JsonResource(builder.build());
    }

    @EnvironmentInterface(value = EnvType.CLIENT, itf = ResourcePackBuilder.Client.class)
    public final class ArtificeResourcePackBuilder implements ResourcePackBuilder.Client, ResourcePackBuilder.Server {
        private ArtificeResourcePackBuilder() {}

        public void setDisplayName(String name) { ArtificeResourcePack.this.displayName = name; }
        public void setDescription(String desc) { ArtificeResourcePack.this.description = desc; }
        public void setVisible() { ArtificeResourcePack.this.visible = true; }
        public void setOptional() {
            ArtificeResourcePack.this.optional = true;
            ArtificeResourcePack.this.visible = true;
        }

        public void add(String id, Resource resource) {
            ArtificeResourcePack.this.resources.put(id, resource);
            ArtificeResourcePack.this.namespaces.add(IdUtils.namespaceOf(id));
        }

        public void addItemModel(String id, Processor<ModelBuilder> f) {
            this.add("models/item/", id, ".json", f, ModelBuilder::new); }
        public void addBlockModel(String id, Processor<ModelBuilder> f) {
            this.add("models/block/", id, ".json", f, ModelBuilder::new); }
        public void addBlockState(String id, Processor<BlockStateBuilder> f) {
            this.add("blockstates/", id, ".json", f, BlockStateBuilder::new); }
        public void addTranslations(String id, Processor<TranslationBuilder> f) {
            this.add("lang/", id, ".json", f, TranslationBuilder::new); }
        public void addParticle(String id, Processor<ParticleBuilder> f) {
            this.add("particles/", id, ".json", f, ParticleBuilder::new); }
        public void addItemAnimation(String id, Processor<AnimationBuilder> f) {
            this.add("textures/item/", id, ".mcmeta", f, AnimationBuilder::new); }
        public void addBlockAnimation(String id, Processor<AnimationBuilder> f) {
            this.add("textures/block/", id, ".mcmeta", f, AnimationBuilder::new); }

        public void addLanguage(LanguageDefinition def) { ArtificeResourcePack.this.languages.add(def); }
        public void addLanguage(String code, String region, String name, boolean rtl) {
            this.addLanguage(new LanguageDefinition(code, region, name, rtl));
        }

        public void addAdvancement(String id, Processor<AdvancementBuilder> f) {
            this.add("advancements/", id, ".json", f, AdvancementBuilder::new); }
        public void addLootTable(String id, Processor<LootTableBuilder> f) {
            this.add("loot_tables/", id, ".json", f, LootTableBuilder::new); }
        public void addItemTag(String id, Processor<TagBuilder> f) {
            this.add("tags/items/", id, ".json", f, TagBuilder::new); }
        public void addBlockTag(String id, Processor<TagBuilder> f) {
            this.add("tags/blocks/", id, ".json", f, TagBuilder::new); }
        public void addEntityTypeTag(String id, Processor<TagBuilder> f) {
            this.add("tags/entity_types/", id, ".json", f, TagBuilder::new); }
        public void addFluidTag(String id, Processor<TagBuilder> f) {
            this.add("tags/fluids/", id, ".json", f, TagBuilder::new); }
        public void addFunctionTag(String id, Processor<TagBuilder> f) {
            this.add("tags/functions/", id, ".json", f, TagBuilder::new); }

        public void addGenericRecipe(String id, Processor<GenericRecipeBuilder> f) {
            this.add("recipes/", id, ".json", f, GenericRecipeBuilder::new); }
        public void addShapedRecipe(String id, Processor<ShapedRecipeBuilder> f) {
            this.add("recipes/", id, ".json", f, ShapedRecipeBuilder::new); }
        public void addShapelessRecipe(String id, Processor<ShapelessRecipeBuilder> f) {
            this.add("recipes/", id, ".json", f, ShapelessRecipeBuilder::new); }
        public void addStonecuttingRecipe(String id, Processor<StonecuttingRecipeBuilder> f) {
            this.add("recipes/", id, ".json", f, StonecuttingRecipeBuilder::new); }

        public void addSmeltingRecipe(String id, Processor<CookingRecipeBuilder> f) {
            this.add("recipes/", id, ".json", r -> f.process(r.type("smelting")), CookingRecipeBuilder::new); }
        public void addBlastingRecipe(String id, Processor<CookingRecipeBuilder> f) {
            this.add("recipes/", id, ".json", r -> f.process(r.type("blasting")), CookingRecipeBuilder::new); }
        public void addSmokingRecipe(String id, Processor<CookingRecipeBuilder> f) {
            this.add("recipes/", id, ".json", r -> f.process(r.type("smoking")), CookingRecipeBuilder::new); }
        public void addCampfireRecipe(String id, Processor<CookingRecipeBuilder> f) {
            this.add("recipes/", id, ".json", r -> f.process(r.type("campfire_cooking")), CookingRecipeBuilder::new); }

        private <T extends TypedJsonBuilder<JsonResource, T>>
        void add(String path, String id, String ext, Processor<T> f, Supplier<T> ctor) {
            this.add(IdUtils.wrapPath(id, path, ext), f.process(ctor.get()).build());
        }
    }

    public static final class Client extends ArtificeResourcePack {
        public Client(String id, Consumer<ResourcePackBuilder.Client> register) { super(id, register); }
        public Resource.Type getType() { return Resource.Type.CLIENT; }
        public boolean isOptional() { return this.optional; }
        public boolean isVisible() { return this.visible; }
        public List<LanguageDefinition> getLanguages() { return new ArrayList<>(this.languages); }

        private static ResourcePackCreator creator = null;
        @SuppressWarnings("ConstantConditions,unchecked")
        public static ResourcePackCreator getCreator() {
            if(creator != null) return creator;
            return creator = new ResourcePackCreator() {
                public <T extends ResourcePackContainer> void registerContainer(Map<String, T> packs, ResourcePackContainer.Factory<T> factory) {
                    for(Client pack : Artifice.ASSETS)
                        packs.put(pack.id, (T)new ArtificeResourcePackContainer(
                            pack.optional, pack.visible,
                            ResourcePackContainer.of(
                                pack.id,
                                false, () -> pack, factory,
                                pack.optional
                                    ? ResourcePackContainer.InsertionPosition.TOP
                                    : ResourcePackContainer.InsertionPosition.BOTTOM
                        )
                    ));
                }
            };
        }
    }

    public static final class Server extends ArtificeResourcePack {
        public Server(String id, Consumer<ResourcePackBuilder.Server> register) { super(id, register); }
        public Resource.Type getType() { return Resource.Type.SERVER; }

        private static ResourcePackCreator creator = null;
        public static ResourcePackCreator getCreator() {
            if(creator != null) return creator;
            return creator = new ResourcePackCreator() {
                public <T extends ResourcePackContainer> void registerContainer(Map<String, T> packs, ResourcePackContainer.Factory<T> factory) {                    System.out.println("registerContainer client");
                    for(Server pack : Artifice.DATA)
                        packs.put(pack.id, ResourcePackContainer.of(
                            pack.id,
                            false, () -> pack, factory,
                            ResourcePackContainer.InsertionPosition.BOTTOM
                        ));
                }
            };
        }
    }

    public String getId() { return id; }
    public Set<String> getNamespaces() { return new HashSet<>(namespaces); }
    public Resource getMetadata() { return metadata; }
    public Map<String, Resource> getResources() { return new HashMap<>(resources); }

    public Resource getResource(String id) throws ResourceNotFoundException {
        if(!this.resources.containsKey(id)) throw new ResourceNotFoundException("Could not find resource "+id);
        return this.resources.get(id);
    }

    public ResourceType getMinecraftType() {
        switch(this.getType()) {
            case CLIENT: return ResourceType.CLIENT_RESOURCES;
            case SERVER: return ResourceType.SERVER_DATA;
            default: return null;
        }
    }

    // --- //

    public InputStream openRoot(String s) {
        if(s.equals(this.getMetadataFilename())) return metadata.toInputStream();
        return new NullInputStream(0);
    }

    public InputStream open(ResourceType type, Identifier id) throws IOException {
        if(!contains(type, id)) throw new FileNotFoundException(id.getPath());
        return this.resources.get(id.toString()).toInputStream();
    }

    public Collection<Identifier> findResources(ResourceType type, String rootFolder, int i, Predicate<String> filter) {
        if(type != this.getMinecraftType()) return new HashSet<>();
        Set<String> keys = new HashSet<>(this.resources.keySet());
        keys.removeIf(id -> !IdUtils.pathOf(id).startsWith(rootFolder) || !filter.test(IdUtils.pathOf(id)));
        return keys.stream().map(Identifier::new).collect(Collectors.toSet());
    }

    public boolean contains(ResourceType resourceType, Identifier id) {
        return resourceType == this.getMinecraftType() && this.resources.containsKey(id.toString()); }
    public Set<String> getNamespaces(ResourceType resourceType) {
        return resourceType == this.getMinecraftType() ? this.namespaces : new HashSet<>(); }

    public <T> T parseMetadata(ResourceMetadataReader<T> reader) {
        return metadata.getData().has(reader.getKey())
            ? reader.fromJson(metadata.getData().getAsJsonObject(reader.getKey()))
            : null;
    }

    public String getName() {
        if(displayName == null) switch(this.getType()) {
            case CLIENT:
//                String aid = Artifice.ASSETS.getId(this);
//                return displayName = aid != null ? aid.toString() : "Generated Resources";
                return displayName = "Generated Resources";
            case SERVER:
//                String did = Artifice.DATA.getId(this);
//                return displayName = did != null ? did.toString() : "Generated Data";
                return displayName = "Generated Data";
        }
        return displayName;
    }

    public void close() {}
}
