package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class GeneratorSettingsBuilder extends TypedJsonBuilder<JsonObject> {
    public GeneratorSettingsBuilder() {
        super(new JsonObject(), j->j);
    }

    public GeneratorSettingsBuilder bedrockRoofPosition(int bedrockRoofPosition) {
        this.root.addProperty("bedrock_roof_position", bedrockRoofPosition);
        return this;
    }

    public GeneratorSettingsBuilder bedrockFloorPosition(int bedrockFloorPosition) {
        this.root.addProperty("bedrock_floor_position", bedrockFloorPosition);
        return this;
    }

    public GeneratorSettingsBuilder seaLevel(int seaLevel) {
        this.root.addProperty("sea_level", seaLevel);
        return this;
    }

    public GeneratorSettingsBuilder noiseConfig(Processor<NoiseConfigBuilder> noiseConfigBuilder) {
        with("noise", JsonObject::new, jsonObject -> noiseConfigBuilder.process(new NoiseConfigBuilder()).buildTo(jsonObject));
        return this;
    }

    public GeneratorSettingsBuilder disableMobGeneration(boolean disableMobGeneration) {
        this.root.addProperty("disable_mob_generation", disableMobGeneration);
        return this;
    }

    private GeneratorSettingsBuilder setBlockState(String id, Processor<BlockStateBuilder> blockStateBuilderProcessor) {
        with(id, JsonObject::new, jsonObject -> blockStateBuilderProcessor.process(new BlockStateBuilder()).buildTo(jsonObject));
        return this;
    }

    public GeneratorSettingsBuilder defaultBlock(Processor<BlockStateBuilder> blockStateBuilderProcessor) {
        return this.setBlockState("default_block", blockStateBuilderProcessor);
    }

    public GeneratorSettingsBuilder defaultFluid(Processor<BlockStateBuilder> blockStateBuilderProcessor) {
        return this.setBlockState("default_fluid", blockStateBuilderProcessor);
    }
    public GeneratorSettingsBuilder structureManager(Processor<StructureManagerBuilder> structureManagerBuilder) {
        with("structures", JsonObject::new, jsonObject -> structureManagerBuilder.process(new StructureManagerBuilder()).buildTo(jsonObject));
        return this;
    }


    public static class BlockStateBuilder extends TypedJsonBuilder<JsonObject> {

        private JsonObject jsonObject = new JsonObject();

        protected BlockStateBuilder() {
            super(new JsonObject(), j->j);
        }

        public BlockStateBuilder name(String id) {
            this.root.addProperty("name", id);
            return this;
        }

        public BlockStateBuilder setProperty(String property, String state) {
            this.jsonObject.addProperty(property, state);
            this.root.add("properties", this.jsonObject);
            return this;
        }
    }
}
