package com.swordglowsblue.artifice.api.builder.data.worldgen.gen;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;

public class TrunkPlacerBuilder extends TypedJsonBuilder<JsonObject> {

    public TrunkPlacerBuilder() {
        super(new JsonObject(), j->j);
    }

    public <P extends TrunkPlacerBuilder> P type(String type) {
        this.root.addProperty("type", type);
        return (P)this;
    }

    public <P extends TrunkPlacerBuilder> P baseHeight(int base_height) {
        if (base_height > 32) throw new IllegalArgumentException("base_height can't be higher than 32! Found " + base_height);
        if (base_height < 0) throw new IllegalArgumentException("base_height can't be smaller than 0! Found " + base_height);
        this.root.addProperty("base_height", base_height);
        return (P) this;
    }

    public <P extends TrunkPlacerBuilder> P heightRandA(int height_rand_a) {
        if (height_rand_a > 24) throw new IllegalArgumentException("height_rand_a can't be higher than 24! Found " + height_rand_a);
        if (height_rand_a < 0) throw new IllegalArgumentException("height_rand_a can't be smaller than 0! Found " + height_rand_a);
        this.root.addProperty("height_rand_a", height_rand_a);
        return (P) this;
    }

    public <P extends TrunkPlacerBuilder> P heightRandB(int height_rand_b) {
        if (height_rand_b > 24) throw new IllegalArgumentException("height_rand_b can't be higher than 24! Found " + height_rand_b);
        if (height_rand_b < 0) throw new IllegalArgumentException("height_rand_b can't be smaller than 0! Found " + height_rand_b);
        this.root.addProperty("height_rand_b", height_rand_b);
        return (P) this;
    }

    public static class StraightTrunkPlacerBuilder extends TrunkPlacerBuilder {

        public StraightTrunkPlacerBuilder() {
            super();
            this.type("minecraft:straight_trunk_placer");
        }
    }

    public static class ForkingTrunkPlacerBuilder extends TrunkPlacerBuilder {

        public ForkingTrunkPlacerBuilder() {
            super();
            this.type("minecraft:forking_trunk_placer");
        }
    }

    public static class GiantTrunkPlacerBuilder extends TrunkPlacerBuilder {

        public GiantTrunkPlacerBuilder() {
            super();
            this.type("minecraft:giant_trunk_placer");
        }
    }

    public static class MegaJungleTrunkPlacerBuilder extends GiantTrunkPlacerBuilder {

        public MegaJungleTrunkPlacerBuilder() {
            super();
            this.type("minecraft:mega_jungle_trunk_placer");
        }
    }

    public static class DarkOakTrunkPlacerBuilder extends TrunkPlacerBuilder {

        public DarkOakTrunkPlacerBuilder() {
            super();
            this.type("minecraft:dark_oak_trunk_placer");
        }
    }

    public static class FancyTrunkPlacerBuilder extends TrunkPlacerBuilder {

        public FancyTrunkPlacerBuilder() {
            super();
            this.type("minecraft:fancy_trunk_placer");
        }
    }
}
