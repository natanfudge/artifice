package com.swordglowsblue.artifice.test;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ArtificeTest {
    private static Identifier id(String name) { return new Identifier("artifice", name); }

    public static void onInitialize() {
        Item.Settings itemSettings = new Item.Settings().group(ItemGroup.MISC);
        Item testItem = Registry.register(Registry.ITEM, id("test_item"), new Item(itemSettings));
        Block testBlock = Registry.register(Registry.BLOCK, id("test_block"), new Block(Block.Settings.copy(Blocks.STONE)));
        Item testBlockItem = Registry.register(Registry.ITEM, id("test_block"), new BlockItem(testBlock, itemSettings));
    }

    @Environment(EnvType.CLIENT)
    public static void onInitializeClient() {
        Artifice.registerAssets("artifice:testmod", ArtificeResourcePack.ofAssets(pack -> {
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
        }));
    }
}
