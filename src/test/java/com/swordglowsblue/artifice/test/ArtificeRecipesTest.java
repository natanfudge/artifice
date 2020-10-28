package com.swordglowsblue.artifice.test;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArtificeRecipesTest {
    @ParameterizedTest
    @ValueSource(strings = {
        "artifice:recipes/test_generic_recipe.json",
        "artifice:recipes/test_shaped_recipe.json",
        "artifice:recipes/test_shapeless_recipe.json",
        "artifice:recipes/test_smelting_recipe.json",
        "artifice:recipes/test_blasting_recipe.json",
        "artifice:recipes/test_smoking_recipe.json",
        "artifice:recipes/test_campfire_recipe.json",
        "artifice:recipes/test_stonecutting_item_recipe.json",
        "artifice:recipes/test_stonecutting_tag_recipe.json",
        "artifice:recipes/test_stonecutting_multi_recipe.json"
    })
    void testRecipeResources(String path) throws IOException {
        assertEquals(Util.readFile("recipes_ref/data/" +path.replace(":","/")), Util.getResource(recipes, path));
    }

    @Test
    void testRecipeMeta() throws IOException {
        assertEquals(recipes.getType(), ResourceType.SERVER_DATA);
        assertEquals(recipes.getName(), "Artifice Test");
        assertEquals(Util.readFile("recipes_ref/pack.mcmeta"), Util.getRootResource(recipes, "pack.mcmeta"));
    }

    @Test
    void testRecipeFiles() throws IOException {
        Util.compareDirectoryToDump(Paths.get(Util.ROOT+"recipes_ref"), "_ref", "_dump");
    }

    private ArtificeResourcePack recipes = ArtificeResourcePack.ofData(pack -> {
        pack.setDisplayName("Artifice Test");
        pack.setDescription("Artifice test pack");

        pack.addGenericRecipe(new Identifier("artifice:test_generic_recipe"), recipe -> recipe
            .type(new Identifier("artifice:recipe"))
            .group(new Identifier("artifice:recipes")));

        pack.addShapedRecipe(new Identifier("artifice:test_shaped_recipe"), recipe -> recipe
            .pattern("XXX", "YZY", "XXX")
            .ingredientItem('X', new Identifier("artifice:ingredient0"))
            .ingredientTag('Y', new Identifier("artifice:ingredient1"))
            .multiIngredient('Z', ingredients -> ingredients
                .item(new Identifier("artifice:ingredient2"))
                .tag(new Identifier("artifice:ingredient3")))
            .result(new Identifier("artifice:result"), 1));

        pack.addShapelessRecipe(new Identifier("artifice:test_shapeless_recipe"), recipe -> recipe
            .ingredientItem(new Identifier("artifice:ingredient0"))
            .ingredientTag(new Identifier("artifice:ingredient1"))
            .multiIngredient(ingredients -> ingredients
                .item(new Identifier("artifice:ingredient2"))
                .tag(new Identifier("artifice:ingredient3")))
            .result(new Identifier("artifice:result"), 1));

        pack.addSmeltingRecipe(new Identifier("artifice:test_smelting_recipe"), recipe -> recipe
            .cookingTime(20)
            .experience(70)
            .ingredientItem(new Identifier("artifice:ingredient"))
            .result(new Identifier("artifice:result")));
        pack.addBlastingRecipe(new Identifier("artifice:test_blasting_recipe"), recipe -> recipe
            .cookingTime(20)
            .experience(70)
            .ingredientTag(new Identifier("artifice:ingredient"))
            .result(new Identifier("artifice:result")));
        pack.addSmokingRecipe(new Identifier("artifice:test_smoking_recipe"), recipe -> recipe
            .cookingTime(20)
            .experience(70)
            .multiIngredient(ingredients -> ingredients
                .item(new Identifier("artifice:ingredient0"))
                .tag(new Identifier("artifice:ingredient1")))
            .result(new Identifier("artifice:result")));
        pack.addCampfireRecipe(new Identifier("artifice:test_campfire_recipe"), recipe -> recipe
            .cookingTime(20)
            .experience(70)
            .ingredientItem(new Identifier("artifice:ingredient"))
            .result(new Identifier("artifice:result")));

        pack.addStonecuttingRecipe(new Identifier("artifice:test_stonecutting_item_recipe"), recipe -> recipe
            .ingredientItem(new Identifier("artifice:ingredient"))
            .result(new Identifier("artifice:result"))
            .count(1));
        pack.addStonecuttingRecipe(new Identifier("artifice:test_stonecutting_tag_recipe"), recipe -> recipe
            .ingredientTag(new Identifier("artifice:ingredient"))
            .result(new Identifier("artifice:result"))
            .count(1));
        pack.addStonecuttingRecipe(new Identifier("artifice:test_stonecutting_multi_recipe"), recipe -> recipe
            .multiIngredient(ingredients -> ingredients
                .item(new Identifier("artifice:ingredient0"))
                .tag(new Identifier("artifice:ingredient1")))
            .result(new Identifier("artifice:result"))
            .count(1));
    });
}
