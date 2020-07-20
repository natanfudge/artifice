package com.swordglowsblue.artifice.api.builder.data.worldgen.gen;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;

public class BlockPlacerBuilder extends TypedJsonBuilder<JsonObject> {

    public BlockPlacerBuilder() {
        super(new JsonObject(), j->j);
    }

    public <P extends BlockPlacerBuilder> P type(String type) {
        this.root.addProperty("type", type);
        return (P) this;
    }

    public static class SimpleBlockPlacerBuilder extends BlockPlacerBuilder {

        public SimpleBlockPlacerBuilder() {
            super();
            this.type("minecraft:simple_block_placer");
        }
    }

    public static class DoublePlantPlacerBuilder extends BlockPlacerBuilder {

        public DoublePlantPlacerBuilder() {
            super();
            this.type("minecraft:double_plant_placer");
        }
    }

    public static class ColumnPlacerBuilder extends BlockPlacerBuilder {

        public ColumnPlacerBuilder() {
            super();
            this.type("minecraft:column_placer");
        }

        public ColumnPlacerBuilder minSize(int minSize) {
            this.root.addProperty("min_size", minSize);
            return this;
        }

        public ColumnPlacerBuilder extraSize(int extraSize) {
            this.root.addProperty("extra_size", extraSize);
            return this;
        }
    }
}
