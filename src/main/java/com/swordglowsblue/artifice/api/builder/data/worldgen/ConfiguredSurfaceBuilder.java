package com.swordglowsblue.artifice.api.builder.data.worldgen;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.builder.data.BlockStateDataBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;

public class ConfiguredSurfaceBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public ConfiguredSurfaceBuilder() {
        super(new JsonObject(), JsonResource::new);
        this.root.add("config", new JsonObject());
    }

    /**
     * Set a block state.
     * @param id
     * @param blockStateBuilderProcessor
     * @return
     */
    private ConfiguredSurfaceBuilder setBlockState(String id, Processor<BlockStateDataBuilder> blockStateBuilderProcessor) {
        with(this.root.getAsJsonObject("config"),id, JsonObject::new, jsonObject -> blockStateBuilderProcessor.process(new BlockStateDataBuilder()).buildTo(jsonObject));
        return this;
    }

    public ConfiguredSurfaceBuilder topMaterial(Processor<BlockStateDataBuilder> blockStateBuilderProcessor) {
        return this.setBlockState("top_material", blockStateBuilderProcessor);
    }

    public ConfiguredSurfaceBuilder underMaterial(Processor<BlockStateDataBuilder> blockStateBuilderProcessor) {
        return this.setBlockState("under_material", blockStateBuilderProcessor);
    }

    public ConfiguredSurfaceBuilder underwaterMaterial(Processor<BlockStateDataBuilder> blockStateBuilderProcessor) {
        return this.setBlockState("underwater_material", blockStateBuilderProcessor);
    }

    public ConfiguredSurfaceBuilder surfaceBuilderID(String id) {
        this.root.addProperty("name", id);
        return this;
    }

}
