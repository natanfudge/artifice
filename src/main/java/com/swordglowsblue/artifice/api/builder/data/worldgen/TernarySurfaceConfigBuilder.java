package com.swordglowsblue.artifice.api.builder.data.worldgen;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.builder.data.BlockStateBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;

public class TernarySurfaceConfigBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public TernarySurfaceConfigBuilder() { super(new JsonObject(), JsonResource::new); }

    /**
     * Set a block state.
     * @param id
     * @param blockStateBuilderProcessor
     * @return
     */
    private TernarySurfaceConfigBuilder setBlockState(String id, Processor<BlockStateBuilder> blockStateBuilderProcessor) {
        with(id, JsonObject::new, jsonObject -> blockStateBuilderProcessor.process(new BlockStateBuilder()).buildTo(jsonObject));
        return this;
    }

    public TernarySurfaceConfigBuilder topMaterial(Processor<BlockStateBuilder> blockStateBuilderProcessor) {
        return this.setBlockState("top_material", blockStateBuilderProcessor);
    }

    public TernarySurfaceConfigBuilder underMaterial(Processor<BlockStateBuilder> blockStateBuilderProcessor) {
        return this.setBlockState("under_material", blockStateBuilderProcessor);
    }

    public TernarySurfaceConfigBuilder underwaterMaterial(Processor<BlockStateBuilder> blockStateBuilderProcessor) {
        return this.setBlockState("underwater_material", blockStateBuilderProcessor);
    }

}
