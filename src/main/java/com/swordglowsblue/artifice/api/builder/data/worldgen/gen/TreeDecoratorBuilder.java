package com.swordglowsblue.artifice.api.builder.data.worldgen.gen;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.BlockStateProviderBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class TreeDecoratorBuilder extends TypedJsonBuilder<JsonObject> {

    public TreeDecoratorBuilder() {
        super(new JsonObject(), j->j);
    }

    public <D extends TreeDecoratorBuilder> D type(String type) {
        this.root.addProperty("type", type);
        return (D) this;
    }

    public static class TrunkVineTreeDecoratorBuilder extends TreeDecoratorBuilder {

        public TrunkVineTreeDecoratorBuilder() {
            super();
            this.type("minecraft:trunk_vine");
        }
    }

    public static class LeaveVineTreeDecoratorBuilder extends TreeDecoratorBuilder {

        public LeaveVineTreeDecoratorBuilder() {
            super();
            this.type("minecraft:leave_vine");
        }
    }

    public static class CocoaTreeDecoratorBuilder extends TreeDecoratorBuilder {

        public CocoaTreeDecoratorBuilder() {
            super();
            this.type("minecraft:cocoa");
        }

        public CocoaTreeDecoratorBuilder probability(float probability) {
            if (probability > 1.0F) throw new IllegalArgumentException("probability can't be higher than 1.0F! Found " + probability);
            if (probability < 0.0F) throw new IllegalArgumentException("probability can't be smaller than 0.0F! Found " + probability);
            this.root.addProperty("probability", probability);
            return this;
        }
    }

    public static class BeeHiveTreeDecoratorBuilder extends TreeDecoratorBuilder {

        public BeeHiveTreeDecoratorBuilder() {
            super();
            this.type("minecraft:beehive");
        }

        public BeeHiveTreeDecoratorBuilder probability(float probability) {
            if (probability > 1.0F) throw new IllegalArgumentException("probability can't be higher than 1.0F! Found " + probability);
            if (probability < 0.0F) throw new IllegalArgumentException("probability can't be smaller than 0.0F! Found " + probability);
            this.root.addProperty("probability", probability);
            return this;
        }
    }

    public static class AlterGroundTreeDecoratorBuilder extends TreeDecoratorBuilder {

        public AlterGroundTreeDecoratorBuilder() {
            super();
            this.type("minecraft:alter_ground");
        }

        public <P extends BlockStateProviderBuilder> AlterGroundTreeDecoratorBuilder provider(Processor<P> processor, P instance) {
            with("provider", JsonObject::new, jsonObject -> processor.process(instance).buildTo(jsonObject));
            return this;
        }
    }
}
