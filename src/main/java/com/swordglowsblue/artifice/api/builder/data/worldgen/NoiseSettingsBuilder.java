package com.swordglowsblue.artifice.api.builder.data.worldgen;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder;
import com.swordglowsblue.artifice.api.builder.data.dimension.NoiseConfigBuilder;
import com.swordglowsblue.artifice.api.builder.data.dimension.StructureManagerBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;

public class NoiseSettingsBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public NoiseSettingsBuilder() {
        super(new JsonObject(), JsonResource::new);
    }

    /**
     * Set the bedrock roof position.
     * @param bedrockRoofPosition
     * @return
     */
    public NoiseSettingsBuilder bedrockRoofPosition(int bedrockRoofPosition) {
        if (bedrockRoofPosition > 276) throw new IllegalArgumentException("bedrockRoofPosition can't be higher than 276! Found " + bedrockRoofPosition);
        if (bedrockRoofPosition < -20) throw new IllegalArgumentException("bedrockRoofPosition can't be smaller than -20! Found " + bedrockRoofPosition);
        this.root.addProperty("bedrock_roof_position", bedrockRoofPosition);
        return this;
    }

    /**
     * Set the bedrock floor position.
     * @param bedrockFloorPosition
     * @return
     */
    public NoiseSettingsBuilder bedrockFloorPosition(int bedrockFloorPosition) {
        if (bedrockFloorPosition > 276) throw new IllegalArgumentException("bedrockFloorPosition can't be higher than 276! Found " + bedrockFloorPosition);
        if (bedrockFloorPosition < -20) throw new IllegalArgumentException("bedrockFloorPosition can't be smaller than -20! Found " + bedrockFloorPosition);
        this.root.addProperty("bedrock_floor_position", bedrockFloorPosition);
        return this;
    }

    /**
     * Set the sea level.
     * @param seaLevel
     * @return
     */
    public NoiseSettingsBuilder seaLevel(int seaLevel) {
        if (seaLevel > 255) throw new IllegalArgumentException("Sealevel can't be higher than 255! Found " + seaLevel);
        if (seaLevel < 0) throw new IllegalArgumentException("Sealevel can't be smaller than 0! Found " + seaLevel);
        this.root.addProperty("sea_level", seaLevel);
        return this;
    }

    /**
     * Build noise config.
     * @param noiseConfigBuilder
     * @return
     */
    public NoiseSettingsBuilder noiseConfig(Processor<NoiseConfigBuilder> noiseConfigBuilder) {
        with("noise", JsonObject::new, jsonObject -> noiseConfigBuilder.process(new NoiseConfigBuilder()).buildTo(jsonObject));
        return this;
    }

    /**
     * @param disableMobGeneration
     * @return
     */
    public NoiseSettingsBuilder disableMobGeneration(boolean disableMobGeneration) {
        this.root.addProperty("disable_mob_generation", disableMobGeneration);
        return this;
    }

    /**
     * Set a block state.
     * @param id
     * @param blockStateBuilderProcessor
     * @return
     */
    public NoiseSettingsBuilder setBlockState(String id, Processor<StateDataBuilder> blockStateBuilderProcessor) {
        with(id, JsonObject::new, jsonObject -> blockStateBuilderProcessor.process(new StateDataBuilder()).buildTo(jsonObject));
        return this;
    }

    /**
     * Build default block.
     * @param blockStateBuilderProcessor
     * @return
     */
    public NoiseSettingsBuilder defaultBlock(Processor<StateDataBuilder> blockStateBuilderProcessor) {
        return this.setBlockState("default_block", blockStateBuilderProcessor);
    }

    /**
     * Build default fluid.
     * @param blockStateBuilderProcessor
     * @return
     */
    public NoiseSettingsBuilder defaultFluid(Processor<StateDataBuilder> blockStateBuilderProcessor) {
        return this.setBlockState("default_fluid", blockStateBuilderProcessor);
    }

    /**
     * Build structure manager.
     * @param structureManagerBuilder
     * @return
     */
    public NoiseSettingsBuilder structureManager(Processor<StructureManagerBuilder> structureManagerBuilder) {
        with("structures", JsonObject::new, jsonObject -> structureManagerBuilder.process(new StructureManagerBuilder()).buildTo(jsonObject));
        return this;
    }
}
