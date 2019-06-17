package com.swordglowsblue.artifice.api;

import com.swordglowsblue.artifice.api.builder.assets.*;
import com.swordglowsblue.artifice.api.builder.data.AdvancementBuilder;
import com.swordglowsblue.artifice.api.builder.data.LootTableBuilder;
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
    }
}
