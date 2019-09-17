package artificemc.artifice.test;

import artificemc.artifice.api.fabric.Artifice;
import artificemc.artifice.api.fabric.ArtificeResourcePack;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

import java.io.IOException;

public class ArtificeTestMod implements ModInitializer, ClientModInitializer {
    public void onInitialize() {
        Item.Settings itemSettings = new Item.Settings().group(ItemGroup.MISC);
        Item testItem = Registry.register(Registry.ITEM, "artifice:test_item", new Item(itemSettings));
        Block testBlock = Registry.register(Registry.BLOCK, "artifice:test_block", new Block(Block.Settings.copy(Blocks.STONE)));
        Item testBlockItem = Registry.register(Registry.ITEM, "artifice:test_block", new BlockItem(testBlock, itemSettings));

        ArtificeResourcePack dataPack = Artifice.registerData("artifice:testmod", pack -> {
            pack.setDisplayName("Artifice Test Data");
            pack.setDescription("Data for the Artifice test mod");

            pack.addShapelessRecipe("artifice:test_item", recipe -> recipe
                .ingredientItem("diamond")
                .ingredientItem("diamond")
                .ingredientItem("diamond")
                .ingredientItem("diamond")
                .result("artifice:test_item", 1));
        });

        try {
            dataPack.dump("../server-dump");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onInitializeClient() {
        ArtificeResourcePack resourcePack = Artifice.registerAssets("artifice:testmod", pack -> {
            pack.setDisplayName("Artifice Test Resources");
            pack.setDescription("Resources for the Artifice test mod");

            pack.addItemModel("artifice:test_item", model -> model
                .parent("item/generated")
                .texture("layer0", "block/sand")
                .texture("layer1", "block/dead_bush"));
            pack.addItemModel("artifice:test_block", model -> model
                .parent("artifice:block/test_block"));

            pack.addBlockState("artifice:test_block", state -> state
                .weightedVariant("", variant -> variant
                    .model("artifice:block/test_block")
                    .weight(3))
                .weightedVariant("", variant -> variant
                    .model("block/coarse_dirt")));

            pack.addBlockModel("artifice:test_block", model -> model
                .parent("block/cube_all")
                .texture("all", "item/diamond_sword"));

            pack.addTranslations("artifice:en_us", translations -> translations
                .entry("item.artifice.test_item", "Artifice Test Item")
                .entry("block.artifice.test_block", "Artifice Test Block"));
            pack.addLanguage("ar_tm", "Artifice", "Test Mod", false);
            pack.addTranslations("artifice:ar_tm", translations -> translations
                .entry("item.artifice.test_item", "Artifice Test Item in custom lang")
                .entry("block.artifice.test_block", "Artifice Test Block in custom lang"));
        });

        Artifice.registerAssets("artifice:testmod2", pack -> {
            pack.setOptional();
        });

        try {
            resourcePack.dump("../client-dump");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
