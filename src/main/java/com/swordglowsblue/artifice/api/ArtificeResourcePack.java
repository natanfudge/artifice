package com.swordglowsblue.artifice.api;

import com.swordglowsblue.artifice.api.builder.assets.*;
import com.swordglowsblue.artifice.api.builder.data.AdvancementBuilder;
import com.swordglowsblue.artifice.api.builder.data.LootTableBuilder;
import com.swordglowsblue.artifice.api.builder.data.TagBuilder;
import com.swordglowsblue.artifice.api.builder.data.dimension.DimensionBuilder;
import com.swordglowsblue.artifice.api.builder.data.dimension.DimensionTypeBuilder;
import com.swordglowsblue.artifice.api.builder.data.recipe.*;
import com.swordglowsblue.artifice.api.builder.data.worldgen.NoiseSettingsBuilder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.biome.BiomeBuilder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.ConfiguredCarverBuilder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.ConfiguredSurfaceBuilder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.ConfiguredFeatureBuilder;
import com.swordglowsblue.artifice.api.resource.ArtificeResource;
import com.swordglowsblue.artifice.api.util.Processor;
import com.swordglowsblue.artifice.api.virtualpack.ArtificeResourcePackContainer;
import com.swordglowsblue.artifice.common.ClientOnly;
import com.swordglowsblue.artifice.common.ClientResourcePackProfileLike;
import com.swordglowsblue.artifice.common.ServerResourcePackProfileLike;
import com.swordglowsblue.artifice.impl.ArtificeResourcePackImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.VanillaDataPackProvider;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * A resource pack containing Artifice-based resources. May be used
 * as a virtual resource pack with {@link Artifice#registerAssets} or {@link Artifice#registerData}.
 */
@SuppressWarnings( {"DeprecatedIsStillUsed"})
public interface ArtificeResourcePack extends ResourcePack, ServerResourcePackProfileLike, ClientResourcePackProfileLike {
    /**
     * @return The {@link ResourceType} this pack contains.
     */
    ResourceType getType();

    /**
     * @return Whether this pack is set as optional
     */
    boolean isOptional();

    /**
     * @return Whether this pack is set as visible in the resource packs menu (only relevant for client-side packs)
     */
    boolean isVisible();

    /**
     * Dump all resources from this pack to the given folder path.
     *
     * @param folderPath The path generated resources should go under (relative to Minecraft's installation folder)
     * @throws IOException              if there is an error creating the necessary directories.
     * @throws IllegalArgumentException if the given path points to a file that is not a directory.
     */
    @Deprecated
    void dumpResources(String folderPath) throws IOException;

    /**
     * The pack will be placed on top of all other packs in order to overwrite them, it will not be optional or visible.
     */
    boolean isShouldOverwrite();

    /**
     * Create a client-side {@link ResourcePackProfile} for this pack.
     *
     * @param factory The factory function passed to {@link VanillaDataPackProvider#register(Consumer, ResourcePackProfile.Factory)}.
     * @return The created container.
     */
    @Override
    @Environment(EnvType.CLIENT)
    default <T extends ResourcePackProfile> ClientOnly<ResourcePackProfile> toClientResourcePackProfile(ResourcePackProfile.Factory factory) {
        return new ClientOnly<>(getAssetsContainer(factory));
    }

    /**
     * Create a server-side {@link ResourcePackProfile} for this pack.
     *
     * @param factory The factory function passed to {@link VanillaDataPackProvider#register}.
     * @return The created container.
     */
    @Override
    default <T extends ResourcePackProfile> ResourcePackProfile toServerResourcePackProfile(ResourcePackProfile.Factory factory) {
        return getDataContainer(factory);
    }

    /**
     * @param factory The factory function passed to {@link VanillaDataPackProvider#register(Consumer, ResourcePackProfile.Factory)}.
     * @return The created container.
     * @deprecated use {@link ArtificeResourcePack#toClientResourcePackProfile(ResourcePackProfile.Factory)}
     * Create a client-side {@link ResourcePackProfile} for this pack.
     */
    @Environment(EnvType.CLIENT)
    @Deprecated
    ArtificeResourcePackContainer getAssetsContainer(ResourcePackProfile.Factory factory);

    /**
     * @param factory The factory function passed to {@link VanillaDataPackProvider#register}.
     * @return The created container.
     * @deprecated use {@link ArtificeResourcePack#toServerResourcePackProfile(ResourcePackProfile.Factory)}
     * Create a server-side {@link ResourcePackProfile} for this pack.
     */
    @Deprecated
    ResourcePackProfile getDataContainer(ResourcePackProfile.Factory factory);

    /**
     * @deprecated  use {@link }
     * Create a new client-side {@link ArtificeResourcePack} and register resources using the given callback.
     *
     * @param register A callback which will be passed a {@link ClientResourcePackBuilder}.
     * @return The created pack.
     */
    @Deprecated
    @Environment(EnvType.CLIENT)
    static ArtificeResourcePack ofAssets(Processor<ClientResourcePackBuilder> register) {
        return new ArtificeResourcePackImpl(ResourceType.CLIENT_RESOURCES, null, register);
    }

    /**
     * Create a new server-side {@link ArtificeResourcePack} and register resources using the given callback.
     *
     * @param register A callback which will be passed a {@link ServerResourcePackBuilder}.
     * @return The created pack.
     */
    @Deprecated
    static ArtificeResourcePack ofData(Processor<ServerResourcePackBuilder> register) {
        return new ArtificeResourcePackImpl(ResourceType.SERVER_DATA, null, register);
    }

    /**
     * Passed to resource construction callbacks to register resources.
     */
    interface ResourcePackBuilder {
        /**
         * Add a resource at the given path.
         *
         * @param id       The resource path.
         * @param resource The resource to add.
         */
        void add(Identifier id, ArtificeResource resource);

        /**
         * Set this pack's display name. Defaults to the pack's ID if not set.
         *
         * @param name The desired name.
         */
        void setDisplayName(String name);

        /**
         * Set this pack's description.
         *
         * @param desc The desired description.
         */
        void setDescription(String desc);

        /**
         * Dumps the pack files
         *
         * @param filePath The path to dump to
         */
        void dumpResources(String filePath, String type) throws IOException;

        /**
         * Mark this pack as optional (can be disabled in the resource packs menu). Will automatically mark it as visible.
         */
        void setOptional();
        
        void shouldOverwrite();
    }

    /**
     * Passed to resource construction callbacks to register client-side resources.
     */
    @Environment(EnvType.CLIENT)
    interface ClientResourcePackBuilder extends ResourcePackBuilder {
        /**
         * Add an item model for the given item ID.
         *
         * @param id An item ID, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link ModelBuilder} to create the item model.
         */
        void addItemModel(Identifier id, Processor<ModelBuilder> f);

        /**
         * Add a block model for the given block ID.
         *
         * @param id A block ID, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link ModelBuilder} to create the block model.
         */
        void addBlockModel(Identifier id, Processor<ModelBuilder> f);

        /**
         * Add a blockstate definition for the given block ID.
         *
         * @param id A block ID, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link BlockStateBuilder} to create the blockstate definition.
         */
        void addBlockState(Identifier id, Processor<BlockStateBuilder> f);

        /**
         * Add a translation file for the given language.
         *
         * @param id The namespace and language code of the desired language.
         * @param f  A callback which will be passed a {@link TranslationBuilder} to create the language file.
         */
        void addTranslations(Identifier id, Processor<TranslationBuilder> f);

        /**
         * Add a particle definition for the given particle ID.
         *
         * @param id A particle ID, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link ParticleBuilder} to create the particle definition.
         */
        void addParticle(Identifier id, Processor<ParticleBuilder> f);

        /**
         * Add a texture animation for the given item ID.
         *
         * @param id An item ID, which will be converted into the correct path.
         * @param f  A callback which will be passed an {@link AnimationBuilder} to create the texture animation.
         */
        void addItemAnimation(Identifier id, Processor<AnimationBuilder> f);

        /**
         * Add a texture animation for the given block ID.
         *
         * @param id A block ID, which will be converted into the correct path.
         * @param f  A callback which will be passed an {@link AnimationBuilder} to create the texture animation.
         */
        void addBlockAnimation(Identifier id, Processor<AnimationBuilder> f);

        /**
         * Add a custom language. Translations must be added separately with {@link ClientResourcePackBuilder#addTranslations}.
         *
         * @param def A {@link LanguageDefinition} for the desired language.
         */
        void addLanguage(LanguageDefinition def);

        /**
         * Add a custom language. Translations must be added separately with {@link ClientResourcePackBuilder#addTranslations}.
         *
         * @param code   The language code for the custom language (i.e. {@code en_us}). Must be all lowercase alphanum / underscores.
         * @param region The name of the language region (i.e. United States).
         * @param name   The name of the language (i.e. English).
         * @param rtl    Whether the language is written right-to-left instead of left-to-right.
         */
        void addLanguage(String code, String region, String name, boolean rtl);



        /**
         * Mark this pack as visible (will be shown in the resource packs menu).
         */
        void setVisible();
    }

    /**
     * Passed to resource construction callbacks to register server-side resources.
     */
    interface ServerResourcePackBuilder extends ResourcePackBuilder {
        /**
         * Add an advancement with the given ID.
         *
         * @param id The ID of the advancement, which will be converted into the correct path.
         * @param f  A callback which will be passed an {@link AdvancementBuilder} to create the advancement.
         */
        void addAdvancement(Identifier id, Processor<AdvancementBuilder> f);

        /**
         * Add a Dimension Type with the given ID.
         *
         * @param id The ID of the dimension type, which will be converted into the correct path.
         * @param f A callback which will be passed an {@link DimensionTypeBuilder} to create the dimension type.
         */
        void addDimensionType(Identifier id, Processor<DimensionTypeBuilder> f);

        /**
         * Add a Dimension with the given ID.
         *
         * @param id The ID of the dimension, which will be converted into the correct path.
         * @param f A callback which will be passed an {@link com.swordglowsblue.artifice.api.builder.data.dimension.DimensionBuilder} to create the dimension.
         */
        void addDimension(Identifier id, Processor<DimensionBuilder> f);

        /**
         * Add a Biome with the given ID.
         *
         * @param id The ID of the biome, which will be converted into the correct path.
         * @param f A callback which will be passed an {@link com.swordglowsblue.artifice.api.builder.data.worldgen.biome.BiomeBuilder} to create the biome.
         */
        void addBiome(Identifier id, Processor<BiomeBuilder> f);

        /**
         * Add a Carver with the given ID.
         *
         * @param id The ID of the carver, which will be converted into the correct path.
         * @param f A callback which will be passed an {@link ConfiguredCarverBuilder} to create the carver.
         */
        void addConfiguredCarver(Identifier id, Processor<ConfiguredCarverBuilder> f);

        /**
         * Add a Feature with the given ID.
         *
         * @param id The ID of the feature, which will be converted into the correct path.
         * @param f A callback which will be passed an {@link ConfiguredFeatureBuilder} to create the feature.
         */
        void addConfiguredFeature(Identifier id, Processor<ConfiguredFeatureBuilder> f);

        /**
         * Add a ConfiguredSurfaceBuilder with the given ID.
         *
         * @param id The ID of the configured surface builder, which will be converted into the correct path.
         * @param f A callback which will be passed an {@link ConfiguredSurfaceBuilder}
         *          to create the configured surface .
         */
        void addConfiguredSurfaceBuilder(Identifier id, Processor<ConfiguredSurfaceBuilder> f);

        /**
         * Add a NoiseSettingsBuilder with the given ID.
         *
         * @param id The ID of the noise settings builder, which will be converted into the correct path.
         * @param f A callback which will be passed an {@link NoiseSettingsBuilder}
         *          to create the noise settings .
         */
        void addNoiseSettingsBuilder(Identifier id, Processor<NoiseSettingsBuilder> f);

        /**
         * Add a loot table with the given ID.
         *
         * @param id The ID of the loot table, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link LootTableBuilder} to create the loot table.
         */
        void addLootTable(Identifier id, Processor<LootTableBuilder> f);

        /**
         * Add an item tag with the given ID.
         *
         * @param id The ID of the tag, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link TagBuilder} to create the tag.
         */
        void addItemTag(Identifier id, Processor<TagBuilder> f);

        /**
         * Add a block tag with the given ID.
         *
         * @param id The ID of the tag, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link TagBuilder} to create the tag.
         */
        void addBlockTag(Identifier id, Processor<TagBuilder> f);

        /**
         * Add an entity type tag with the given ID.
         *
         * @param id The ID of the tag, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link TagBuilder} to create the tag.
         */
        void addEntityTypeTag(Identifier id, Processor<TagBuilder> f);

        /**
         * Add a fluid tag with the given ID.
         *
         * @param id The ID of the tag, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link TagBuilder} to create the tag.
         */
        void addFluidTag(Identifier id, Processor<TagBuilder> f);

        /**
         * Add a function tag with the given ID.
         *
         * @param id The ID of the tag, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link TagBuilder} to create the tag.
         */
        void addFunctionTag(Identifier id, Processor<TagBuilder> f);

        /**
         * Add a recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link GenericRecipeBuilder} to create the recipe.
         */
        void addGenericRecipe(Identifier id, Processor<GenericRecipeBuilder> f);

        /**
         * Add a shaped crafting recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link ShapedRecipeBuilder} to create the recipe.
         */
        void addShapedRecipe(Identifier id, Processor<ShapedRecipeBuilder> f);

        /**
         * Add a shapeless crafting recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link ShapelessRecipeBuilder} to create the recipe.
         */
        void addShapelessRecipe(Identifier id, Processor<ShapelessRecipeBuilder> f);

        /**
         * Add a stonecutter recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link StonecuttingRecipeBuilder} to create the recipe.
         */
        void addStonecuttingRecipe(Identifier id, Processor<StonecuttingRecipeBuilder> f);

        /**
         * Add a smelting recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link CookingRecipeBuilder} to create the recipe.
         */
        void addSmeltingRecipe(Identifier id, Processor<CookingRecipeBuilder> f);

        /**
         * Add a blast furnace recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link CookingRecipeBuilder} to create the recipe.
         */
        void addBlastingRecipe(Identifier id, Processor<CookingRecipeBuilder> f);

        /**
         * Add a smoker recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link CookingRecipeBuilder} to create the recipe.
         */
        void addSmokingRecipe(Identifier id, Processor<CookingRecipeBuilder> f);

        /**
         * Add a campfire recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link CookingRecipeBuilder} to create the recipe.
         */
        void addCampfireRecipe(Identifier id, Processor<CookingRecipeBuilder> f);

        /**
         * Add a smithing table recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a {@link CookingRecipeBuilder} to create the recipe.
         */
        void addSmithingRecipe(Identifier id, Processor<SmithingRecipeBuilder> f);
    }
}
