package com.swordglowsblue.artifice.api.builder.data.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class BlockStateProviderBuilder extends TypedJsonBuilder<JsonObject> {

    public BlockStateProviderBuilder() {
        super(new JsonObject(), j->j);
    }

    public <P extends BlockStateProviderBuilder> P type(String type) {
        this.root.addProperty("type", type);
        return (P)this;
    }

    public static class SimpleBlockStateProviderBuilder extends BlockStateProviderBuilder {
        public SimpleBlockStateProviderBuilder() {
            super();
            this.type("minecraft:simple_state_provider");
        }

        public SimpleBlockStateProviderBuilder state(Processor<StateDataBuilder> processor) {
            with("state", JsonObject::new, jsonObject -> processor.process(new StateDataBuilder()).buildTo(jsonObject));
            return this;
        }
    }

    public static class WeightedBlockStateProviderBuilder extends BlockStateProviderBuilder {
        public WeightedBlockStateProviderBuilder() {
            super();
            this.type("minecraft:weighted_state_provider");
            this.root.add("entries", new JsonArray());
        }

        public WeightedBlockStateProviderBuilder addEntry(int weight, Processor<StateDataBuilder> processor) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("weight", weight);
            jsonObject.add("data", processor.process(new StateDataBuilder()).buildTo(new JsonObject()));
            this.root.getAsJsonArray("entries").add(jsonObject);
            return this;
        }
    }

    public static class PlainFlowerBlockStateProviderBuilder extends BlockStateProviderBuilder {
        public PlainFlowerBlockStateProviderBuilder() {
            super();
            this.type("minecraft:plain_flower_provider");
        }
    }

    public static class ForestFlowerBlockStateProviderBuilder extends BlockStateProviderBuilder {
        public ForestFlowerBlockStateProviderBuilder() {
            super();
            this.type("minecraft:forest_flower_provider");
        }
    }

    public static class PillarBlockStateProviderBuilder extends BlockStateProviderBuilder {
        public PillarBlockStateProviderBuilder() {
            super();
            this.type("minecraft:pillar_state_provider");
        }

        public PillarBlockStateProviderBuilder state(Processor<StateDataBuilder> processor) {
            with("state", JsonObject::new, jsonObject -> processor.process(new StateDataBuilder()).buildTo(jsonObject));
            return this;
        }
    }
}
