package com.swordglowsblue.artifice.test;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.impl.resource_types.BlockModelBuilder;
import com.swordglowsblue.artifice.impl.resource_types.ItemModelBuilder;
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
            pack.add(new Identifier("artifice:models/item/test_item.json"), new ItemModelBuilder()
                .parent(new Identifier("item/generated"))
                .texture("layer0", new Identifier("block/sand"))
                .texture("layer1", new Identifier("block/dead_bush"))
                .build());
            pack.add(new Identifier("artifice:models/block/test_block.json"), new BlockModelBuilder()
                .parent(new Identifier("block/cube_all"))
                .texture("all", new Identifier("item/diamond_sword"))
                .build());
            pack.add(new Identifier("artifice:models/item/test_block.json"), new ItemModelBuilder()
                .parent(new Identifier("artifice:block/test_block"))
                .build());
        }));
    }
}
