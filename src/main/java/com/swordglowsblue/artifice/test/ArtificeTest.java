package com.swordglowsblue.artifice.test;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ArtificeTest {
    public static void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("artifice:test_item"), new Item(new Item.Settings().group(ItemGroup.MISC)));
    }

    @Environment(EnvType.CLIENT)
    public static void onInitializeClient() {
        Artifice.registerAssets("artifice:testmod", ArtificeResourcePack.ofAssets(pack -> {
            pack.add(new Identifier("artifice:models/item/test_item.json"), "{ \"parent\":\"item/generated\", \"textures\": { \"layer0\": \"block/dead_bush\" }}");
        }));
    }
}
