package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.builder.data.BlockStateDataBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class GeneratorSettingsBuilder extends TypedJsonBuilder<JsonObject> {
    public GeneratorSettingsBuilder() {
        super(new JsonObject(), j->j);
    }

    /**
     * Set the bedrock roof position.
     * @param bedrockRoofPosition
     * @return
     */
    public GeneratorSettingsBuilder bedrockRoofPosition(int bedrockRoofPosition) {
        this.root.addProperty("bedrock_roof_position", bedrockRoofPosition);
        return this;
    }

    /**
     * Set the bedrock floor position.
     * @param bedrockFloorPosition
     * @return
     */
    public GeneratorSettingsBuilder bedrockFloorPosition(int bedrockFloorPosition) {
        this.root.addProperty("bedrock_floor_position", bedrockFloorPosition);
        return this;
    }

    /**
     * Set the sea level.
     * @param seaLevel
     * @return
     */
    public GeneratorSettingsBuilder seaLevel(int seaLevel) {
        this.root.addProperty("sea_level", seaLevel);
        return this;
    }

    /**
     * Build noise config.
     * @param noiseConfigBuilder
     * @return
     */
    public GeneratorSettingsBuilder noiseConfig(Processor<NoiseConfigBuilder> noiseConfigBuilder) {
        with("noise", JsonObject::new, jsonObject -> noiseConfigBuilder.process(new NoiseConfigBuilder()).buildTo(jsonObject));
        return this;
    }

    /**
     * @param disableMobGeneration
     * @return
     */
    public GeneratorSettingsBuilder disableMobGeneration(boolean disableMobGeneration) {
        this.root.addProperty("disable_mob_generation", disableMobGeneration);
        return this;
    }

    /**
     * Set a block state.
     * @param id
     * @param blockStateBuilderProcessor
     * @return
     */
    public GeneratorSettingsBuilder setBlockState(String id, Processor<BlockStateDataBuilder> blockStateBuilderProcessor) {
        with(id, JsonObject::new, jsonObject -> blockStateBuilderProcessor.process(new BlockStateDataBuilder()).buildTo(jsonObject));
        return this;
    }

    /**
     * Build default block.
     * @param blockStateBuilderProcessor
     * @return
     */
    public GeneratorSettingsBuilder defaultBlock(Processor<BlockStateDataBuilder> blockStateBuilderProcessor) {
        return this.setBlockState("default_block", blockStateBuilderProcessor);
    }

    /**
     * Build default fluid.
     * @param blockStateBuilderProcessor
     * @return
     */
    public GeneratorSettingsBuilder defaultFluid(Processor<BlockStateDataBuilder> blockStateBuilderProcessor) {
        return this.setBlockState("default_fluid", blockStateBuilderProcessor);
    }

    /**
     * Build structure manager.
     * @param structureManagerBuilder
     * @return
     */
    public GeneratorSettingsBuilder structureManager(Processor<StructureManagerBuilder> structureManagerBuilder) {
        with("structures", JsonObject::new, jsonObject -> structureManagerBuilder.process(new StructureManagerBuilder()).buildTo(jsonObject));
        return this;
    }
}
