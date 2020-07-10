package com.swordglowsblue.artifice.test;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.api.builder.data.dimension.BiomeSourceBuilder;
import com.swordglowsblue.artifice.api.builder.data.dimension.ChunkGeneratorTypeBuilder;
import com.swordglowsblue.artifice.api.resource.StringResource;
import com.swordglowsblue.artifice.api.util.Processor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.StructureFeature;

public class ArtificeTestMod implements ModInitializer, ClientModInitializer {
    private static Identifier id(String name) { return new Identifier("artifice", name); }

    private static final Item.Settings itemSettings = new Item.Settings().group(ItemGroup.MISC);
    private static final Item testItem = Registry.register(Registry.ITEM, id("test_item"), new Item(itemSettings));
    private static final Block testBlock = Registry.register(Registry.BLOCK, id("test_block"), new Block(Block.Settings.copy(Blocks.STONE)));
    private static final Item testBlockItem = Registry.register(Registry.ITEM, id("test_block"), new BlockItem(testBlock, itemSettings));

    private static final RegistryKey<DimensionType> testDimension = RegistryKey.of(Registry.DIMENSION_TYPE_KEY,id("test_dimension_type_vanilla"));
    private static final RegistryKey<DimensionType> testDimensionCustom = RegistryKey.of(Registry.DIMENSION_TYPE_KEY,id("test_dimension_type_custom"));

    public void onInitialize() {
        Registry.register(Registry.CHUNK_GENERATOR, RegistryKey.of(Registry.DIMENSION,id("test_chunk_generator")).getValue(), TestChunkGenerator.CODEC);
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
                            "      \"item\": \"minecraft:stone\"\n" +
                            "    }\n" +
                            "  },\n" +
                            "  \"result\": {\n" +
                            "    \"item\": \"artifice:test_item\",\n" +
                            "    \"count\": 3\n" +
                            "  }\n" +
                            "}"));

            pack.addDimensionType(testDimension.getValue(), dimensionTypeBuilder -> {
                dimensionTypeBuilder
                        .natural(true).hasRaids(false).respawnAnchorWorks(false).bedWorks(false).piglinSafe(false)
                        .ambientLight(0.0F).infiniburn(BlockTags.INFINIBURN_OVERWORLD.getId())
                        .ultrawarm(false).hasCeiling(false).hasSkylight(true).shrunk(false).logicalHeight(256);
            });
            pack.addDimension(id("test_dimension"), dimensionBuilder -> {
                dimensionBuilder.dimensionType(testDimension.getValue()).flatGenerator(flatChunkGeneratorTypeBuilder -> {
                    flatChunkGeneratorTypeBuilder.addLayer(layersBuilder -> {
                        layersBuilder.block("minecraft:bedrock").height(2);
                    }).addLayer(layersBuilder -> {
                        layersBuilder.block("minecraft:stone").height(2);
                    }).addLayer(layersBuilder -> {
                        layersBuilder.block("minecraft:cobblestone").height(2);
                    }).biome("minecraft:plains")
                            .structureManager(structureManagerBuilder -> {
                        structureManagerBuilder.addStructure(Registry.STRUCTURE_FEATURE.getId(StructureFeature.MINESHAFT).toString(),
                                structureConfigBuilder -> {
                            structureConfigBuilder.salt(1999999).separation(1).spacing(2);
                        });
                    })
                    ;
                });
            });

            pack.addDimensionType(testDimensionCustom.getValue(), dimensionTypeBuilder -> {
                dimensionTypeBuilder
                        .natural(true).hasRaids(false).respawnAnchorWorks(false).bedWorks(false).piglinSafe(false)
                        .ambientLight(0.0F).infiniburn(BlockTags.INFINIBURN_OVERWORLD.getId())
                        .ultrawarm(false).hasCeiling(false).hasSkylight(true).shrunk(false).logicalHeight(256);
            });
            pack.addDimension(id("test_dimension_custom"), dimensionBuilder -> {
                dimensionBuilder.dimensionType(testDimensionCustom.getValue()).generator(testChunkGeneratorTypeBuilder -> {
                    testChunkGeneratorTypeBuilder.testBool(true).biomeSource(biomeSourceBuilder -> {
                        biomeSourceBuilder.biome("minecraft:taiga");
                    }, new BiomeSourceBuilder.FixedBiomeSourceBuilder());
                }, new TestChunkGeneratorTypeBuilder());
            });
        });
    }

    @Environment(EnvType.CLIENT)
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

    public static class TestChunkGenerator extends ChunkGenerator {
        public static final Codec<TestChunkGenerator> CODEC = RecordCodecBuilder.create((instance) ->
                instance.group(
                        BiomeSource.field_24713.fieldOf("biome_source")
                                .forGetter((generator) -> generator.biomeSource),
                        Codec.BOOL.fieldOf("test_bool").forGetter((generator) -> generator.testBool)
                )
                        .apply(instance, instance.stable(TestChunkGenerator::new))
        );

        private boolean testBool;

        public TestChunkGenerator(BiomeSource biomeSource, boolean testBool) {
            super(biomeSource, new StructuresConfig(false));
            this.testBool = testBool;
        }

        @Override
        protected Codec<? extends ChunkGenerator> method_28506() {
            return CODEC;
        }

        @Override
        public ChunkGenerator withSeed(long seed) {
            return this;
        }

        @Override
        public void buildSurface(ChunkRegion region, Chunk chunk) {
        }

        @Override
        public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
        }

        @Override
        public int getHeight(int x, int z, Heightmap.Type heightmapType) {
            return 0;
        }

        @Override
        public BlockView getColumnSample(int x, int z) {
            return new VerticalBlockSample(new BlockState[0]);
        }
    }

    public static class TestChunkGeneratorTypeBuilder extends ChunkGeneratorTypeBuilder {
        public TestChunkGeneratorTypeBuilder() {
            super();
            this.type(id("test_chunk_generator").toString());
        }

        public TestChunkGeneratorTypeBuilder testBool(boolean customBool) {
            this.root.addProperty("test_bool", customBool);
            return this;
        }
    }
}
