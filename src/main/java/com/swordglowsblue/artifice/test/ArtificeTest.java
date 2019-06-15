package com.swordglowsblue.artifice.test;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.impl.resource.StringResource;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ArtificeTest implements ModInitializer, ClientModInitializer {
    private static Identifier id(String name) { return new Identifier("artifice", name); }

    public void onInitialize() {
        if(!FabricLoader.getInstance().isDevelopmentEnvironment()) return;

        Item.Settings itemSettings = new Item.Settings().group(ItemGroup.MISC);
        Item testItem = Registry.register(Registry.ITEM, id("test_item"), new Item(itemSettings));
        Block testBlock = Registry.register(Registry.BLOCK, id("test_block"), new Block(Block.Settings.copy(Blocks.STONE)));
        Item testBlockItem = Registry.register(Registry.ITEM, id("test_block"), new BlockItem(testBlock, itemSettings));

        Artifice.registerData("artifice:testmod", ArtificeResourcePack.ofData(pack -> {
            pack.add(new Identifier("artifice:recipes/test_item.json"), new StringResource("{}"));
        }));
    }

    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        if(!FabricLoader.getInstance().isDevelopmentEnvironment()) return;

        Artifice.registerAssets("artifice:testmod", ArtificeResourcePack.ofAssets(false, pack -> {
            pack.addItemModel(new Identifier("artifice:test_item"), model -> model
                .parent(new Identifier("item/generated"))
                .texture("layer0", new Identifier("block/sand"))
                .texture("layer1", new Identifier("block/dead_bush")));
            pack.addItemModel(new Identifier("artifice:test_block"), model -> model
                .parent(new Identifier("artifice:block/test_block")));

            pack.addBlockState(new Identifier("artifice:test_block"), state -> state
                .weightedVariant("", variant -> variant
                    .model(new Identifier("artifice:block/test_block"))
                    .weight(3))
                .weightedVariant("", variant -> variant
                    .model(new Identifier("block/coarse_dirt"))));

            pack.addBlockModel(new Identifier("artifice:test_block"), model -> model
                .parent(new Identifier("block/cube_all"))
                .texture("all", new Identifier("item/diamond_sword")));

            pack.addTranslations(new Identifier("artifice:en_us"), translations -> translations
                .entry("item.artifice.test_item", "Artifice Test Item")
                .entry("block.artifice.test_block", "Artifice Test Block"));
//            pack.addLanguage(new Identifier("artifice:artifice"), new LanguageDefinition("artifice", "Artifice", "Artifice", false), t->t
//                .entry("item.artifice.test_item", "Artifice Test Item in custom lang")
//                .entry("block.artifice.test_block", "Artifice Test Block in custom lang"));
        }));

        Artifice.registerAssets(new Identifier("artifice:testmod2"), ArtificeResourcePack.ofAssets(true, pack -> {}));
    }
}
