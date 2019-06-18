package com.swordglowsblue.artifice.api;

import com.swordglowsblue.artifice.api.builder.assets.*;
import com.swordglowsblue.artifice.api.builder.data.AdvancementBuilder;
import com.swordglowsblue.artifice.api.builder.data.LootTableBuilder;
import com.swordglowsblue.artifice.api.builder.data.TagBuilder;
import com.swordglowsblue.artifice.api.builder.data.recipe.*;
import com.swordglowsblue.artifice.impl.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public interface ArtificeResourcePack extends ResourcePack {
    ResourceType getType();
    boolean isOptional();
    boolean isVisible();

    interface ResourceRegistry {
        void add(Identifier id, ArtificeResource resource);
        void setDisplayName(String name);
        void setDescription(String desc);
    }

    @Environment(EnvType.CLIENT)
    interface ClientResourceRegistry extends ResourceRegistry {
        void addItemModel(Identifier id, Processor<ModelBuilder> f);
        void addBlockModel(Identifier id, Processor<ModelBuilder> f);
        void addBlockState(Identifier id, Processor<BlockStateBuilder> f);
        void addTranslations(Identifier id, Processor<TranslationBuilder> f);
        void addParticle(Identifier id, Processor<ParticleBuilder> f);
        void addItemAnimation(Identifier id, Processor<AnimationBuilder> f);
        void addBlockAnimation(Identifier id, Processor<AnimationBuilder> f);
        void addLanguage(LanguageDefinition def);
        void addLanguage(String code, String region, String name, boolean rtl);

        void setOptional();
        void setVisible();
    }

    interface ServerResourceRegistry extends ResourceRegistry {
        void addAdvancement(Identifier id, Processor<AdvancementBuilder> f);
        void addLootTable(Identifier id, Processor<LootTableBuilder> f);
        void addItemTag(Identifier id, Processor<TagBuilder> f);
        void addBlockTag(Identifier id, Processor<TagBuilder> f);
        void addEntityTypeTag(Identifier id, Processor<TagBuilder> f);
        void addFluidTag(Identifier id, Processor<TagBuilder> f);
        void addFunctionTag(Identifier id, Processor<TagBuilder> f);

        void addGenericRecipe(Identifier id, Processor<GenericRecipeBuilder> f);
        void addShapedRecipe(Identifier id, Processor<ShapedRecipeBuilder> f);
        void addShapelessRecipe(Identifier id, Processor<ShapelessRecipeBuilder> f);
        void addStonecuttingRecipe(Identifier id, Processor<StonecuttingRecipeBuilder> f);
        void addSmeltingRecipe(Identifier id, Processor<CookingRecipeBuilder> f);
        void addBlastingRecipe(Identifier id, Processor<CookingRecipeBuilder> f);
        void addSmokingRecipe(Identifier id, Processor<CookingRecipeBuilder> f);
        void addCampfireRecipe(Identifier id, Processor<CookingRecipeBuilder> f);
    }
}
