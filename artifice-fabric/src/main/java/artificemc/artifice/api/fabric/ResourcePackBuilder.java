package artificemc.artifice.api.fabric;

import artificemc.artifice.api.builders.client.*;
import artificemc.artifice.api.builders.server.*;
import artificemc.artifice.api.core.resource.Resource;
import artificemc.artifice.api.core.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.LanguageDefinition;

public interface ResourcePackBuilder {
    void add(String id, Resource resource);
    void setDisplayName(String name);
    void setDescription(String desc);

    @Environment(EnvType.CLIENT)
    interface Client extends ResourcePackBuilder {
        void addItemModel(String id, Processor<ModelBuilder> f);
        void addBlockModel(String id, Processor<ModelBuilder> f);
        void addBlockState(String id, Processor<BlockStateBuilder> f);
        void addTranslations(String id, Processor<TranslationBuilder> f);
        void addParticle(String id, Processor<ParticleBuilder> f);
        void addItemAnimation(String id, Processor<AnimationBuilder> f);
        void addBlockAnimation(String id, Processor<AnimationBuilder> f);
        void addLanguage(LanguageDefinition def);
        void addLanguage(String code, String region, String name, boolean rtl);
        void setOptional();
        void setVisible();
    }

    interface Server extends ResourcePackBuilder {
        void addAdvancement(String id, Processor<AdvancementBuilder> f);
        void addLootTable(String id, Processor<LootTableBuilder> f);
        void addItemTag(String id, Processor<TagBuilder> f);
        void addBlockTag(String id, Processor<TagBuilder> f);
        void addEntityTypeTag(String id, Processor<TagBuilder> f);
        void addFluidTag(String id, Processor<TagBuilder> f);
        void addFunctionTag(String id, Processor<TagBuilder> f);
        void addGenericRecipe(String id, Processor<GenericRecipeBuilder> f);
        void addShapedRecipe(String id, Processor<ShapedRecipeBuilder> f);
        void addShapelessRecipe(String id, Processor<ShapelessRecipeBuilder> f);
        void addStonecuttingRecipe(String id, Processor<StonecuttingRecipeBuilder> f);
        void addSmeltingRecipe(String id, Processor<CookingRecipeBuilder> f);
        void addBlastingRecipe(String id, Processor<CookingRecipeBuilder> f);
        void addSmokingRecipe(String id, Processor<CookingRecipeBuilder> f);
        void addCampfireRecipe(String id, Processor<CookingRecipeBuilder> f);
    }
}
