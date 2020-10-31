package com.swordglowsblue.artifice.test;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ArtificeAssetsTest {
    @ParameterizedTest
    @ValueSource(strings = {
        "artifice:models/item/test_item_model.json",
        "artifice:models/block/test_block_model.json",
        "artifice:blockstates/test_blockstate_variants.json",
        "artifice:blockstates/test_blockstate_multipart.json",
        "artifice:textures/item/test_item_animation.mcmeta",
        "artifice:textures/block/test_block_animation.mcmeta",
        "artifice:particles/test_particle.json",
        "artifice:lang/test_language.json"
    })
    void testAssetResources(String path) throws IOException {
        assertEquals(Util.readFile("assets_ref/assets/" +path.replace(":","/")), Util.getResource(assets, path));
    }

    @Test
    void testAssetMeta() throws IOException {
        assertEquals(assets.getType(), ResourceType.CLIENT_RESOURCES);
        assertEquals(assets.getName(), "Artifice Test");
        assertEquals(Util.readFile("assets_ref/pack.mcmeta"), Util.getRootResource(assets, "pack.mcmeta"));
        assertTrue(assets.isOptional());
        assertTrue(assets.isVisible());
        assertFalse(assets.isShouldOverwrite());
    }

    @Test
    void testshouldReplace() throws IOException{
        assertTrue(assetsoverwrite.isShouldOverwrite());
    }

    @Test
    void testAssetFiles() throws IOException {
        Util.compareDirectoryToDump(Paths.get(Util.ROOT+"assets_ref"), "_ref", "_dump");
    }

    private ArtificeResourcePack assets = ArtificeResourcePack.ofAssets(pack -> {
        pack.setDisplayName("Artifice Test");
        pack.setDescription("Artifice test pack");
        pack.setOptional();

        pack.addItemModel(new Identifier("artifice:test_item_model"), model -> model
            .parent(new Identifier("artifice:parent"))
            .texture("layer0", new Identifier("artifice:layer0"))
            .texture("layer1", new Identifier("artifice:layer1"))
            .display("test_display", display -> display
                .rotation(0, 1, -2)
                .translation(0, 1, -2)
                .scale(0, 1, -2))
            .element(element -> element
                .from(0, 1, -2)
                .to(0, 1, -2)
                .shade(false)
                .rotation(rotation -> rotation
                    .angle(22.5f)
                    .axis(Direction.Axis.X)
                    .origin(0, 1, -2)
                    .rescale(false))
                .face(Direction.DOWN, face -> face
                    .texture(new Identifier("artifice:face_texture"))
                    .cullface(Direction.DOWN)
                    .rotation(180)
                    .tintindex(0)
                    .uv(0, 0, 16, 16)))
            .override(override -> override
                .predicate("test_predicate", 0)
                .model(new Identifier("artifice:test_model_override"))));

        pack.addBlockModel(new Identifier("artifice:test_block_model"), model -> model
            .parent(new Identifier("artifice:parent"))
            .ambientocclusion(true));

        pack.addBlockState(new Identifier("artifice:test_blockstate_variants"), state -> state
            .variant("variant=0", variant -> variant
                .model(new Identifier("artifice:model"))
                .rotationX(90)
                .rotationY(180)
                .uvlock(true))
            .weightedVariant("variant=1", variant -> variant.model(new Identifier("artifice:model0")).weight(0))
            .weightedVariant("variant=1", variant -> variant.model(new Identifier("artifice:model1")).weight(1)));

        pack.addBlockState(new Identifier("artifice:test_blockstate_multipart"), state -> state
            .multipartCase(c -> c
                .when("variant", "0")
                .apply(variant -> variant.model(new Identifier("artifice:model"))))
            .multipartCase(c -> c
                .whenAny("variant", "1|2")
                .whenAny("other", "3")
                .weightedApply(variant -> variant.model(new Identifier("artifice:model0")).weight(0))
                .weightedApply(variant -> variant.model(new Identifier("artifice:model1")).weight(1))));

        pack.addItemAnimation(new Identifier("artifice:test_item_animation"), anim -> anim
            .interpolate(true).width(1).height(7).frametime(1)
            .frames(order -> order.frame(0, 0).frameRange(1,7).frame(4).frame(2)));
        pack.addBlockAnimation(new Identifier("artifice:test_block_animation"), anim -> anim
            .interpolate(true).width(1).height(7).frametime(1)
            .frames(order -> order.frame(0, 0).frameRange(1,7).frame(4).frame(2)));

        pack.addParticle(new Identifier("artifice:test_particle"), particle -> particle
            .texture(new Identifier("artifice:texture0"))
            .texture(new Identifier("artifice:texture1")));

        pack.addLanguage("test_language", "Testing", "Test Language", false);
        pack.addTranslations(new Identifier("artifice:test_language"), lang -> lang
            .entry("test1", "Test 1")
            .entry("test2", "Test 2"));
    });

    private ArtificeResourcePack assetsoverwrite = ArtificeResourcePack.ofAssets(pack -> {
        pack.setDisplayName("Artifice Overwrite Test");
        pack.setDescription("Artifice test pack");
        pack.shouldOverwrite();
    });
}
