package com.swordglowsblue.artifice.test;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.api.resource.StringResource;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ArtificeTestMod implements ModInitializer, ClientModInitializer {
    private static Identifier id(String name) { return new Identifier("artifice", name); }

    private static final Item.Settings itemSettings = new Item.Settings().group(ItemGroup.MISC);
    private static final Item testItem = Registry.register(Registry.ITEM, id("test_item"), new Item(itemSettings));
    private static final Block testBlock = Registry.register(Registry.BLOCK, id("test_block"), new Block(Block.Settings.copy(Blocks.STONE)));
    private static final Item testBlockItem = Registry.register(Registry.ITEM, id("test_block"), new BlockItem(testBlock, itemSettings));

    public void onInitialize() {
        ArtificeResourcePack dataPack = Artifice.registerData(id("testmod"), pack -> {
            pack.setDisplayName("Artifice Test Data");
            pack.setDescription("Data for the Artifice test mod");

            pack.add(id("recipes/test_item.json"), new StringResource("{\n" +
                            "  \"type\": \"minecraft:crafting_shaped\",\n" +
                            "  \"group\": \"wooden_door\",\n" +
                            "  \"pattern\": [\n" +
                            "    \"##\",\n" +
                            "    \"##\",\n" +
                            "    \"##\"\n" +
                            "  ],\n" +
                            "  \"key\": {\n" +
                            "    \"#\": {\n" +
                            "      \"item\": \"minecraft:acacia_planks\"\n" +
                            "    }\n" +
                            "  },\n" +
                            "  \"result\": {\n" +
                            "    \"item\": \"artifice:test_item\",\n" +
                            "    \"count\": 3\n" +
                            "  }\n" +
                            "}"));
        });
    }

    public void onInitializeClient() {
        ArtificeResourcePack resourcePack = Artifice.registerAssets("artifice:testmod", pack -> {
            pack.setDisplayName("Artifice Test Resources");
            pack.setDescription("Resources for the Artifice test mod");

            pack.addItemModel(id("test_item"), model -> model
                .parent(new Identifier("item/generated"))
                .texture("layer0", new Identifier("block/sand"))
                .texture("layer1", new Identifier("block/dead_bush")));
            pack.addItemModel(id("test_block"), model -> model
                .parent(id("block/test_block")));

            pack.addBlockState(id("test_block"), state -> state
                .weightedVariant("", variant -> variant
                    .model(id("block/test_block"))
                    .weight(3))
                .weightedVariant("", variant -> variant
                    .model(new Identifier("block/coarse_dirt"))));

            pack.addBlockModel(id("test_block"), model -> model
                .parent(new Identifier("block/cube_all"))
                .texture("all", new Identifier("item/diamond_sword")));

            pack.addTranslations(id("en_us"), translations -> translations
                .entry("item.artifice.test_item", "Artifice Test Item")
                .entry("block.artifice.test_block", "Artifice Test Block"));
            pack.addLanguage("ar_tm", "Artifice", "Test Mod", false);
            pack.addTranslations(id("ar_tm"), translations -> translations
                .entry("item.artifice.test_item", "Artifice Test Item in custom lang")
                .entry("block.artifice.test_block", "Artifice Test Block in custom lang"));
        });

        Artifice.registerAssets(id("testmod2"), pack -> {
            pack.setOptional();
        });
    }
}
