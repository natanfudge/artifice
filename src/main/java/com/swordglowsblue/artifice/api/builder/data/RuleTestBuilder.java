package com.swordglowsblue.artifice.api.builder.data;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class RuleTestBuilder extends TypedJsonBuilder<JsonObject> {

    public RuleTestBuilder() {
        super(new JsonObject(), j->j);
    }

    public <R extends RuleTestBuilder> R predicateType(String type) {
        this.root.addProperty("predicate_type", type);
        return (R) this;
    }

    public static class AlwaysTrueRuleTestBuilder extends RuleTestBuilder {

        public AlwaysTrueRuleTestBuilder() {
            super();
            this.predicateType("minecraft:always_true");
        }
    }

    public static class BlockRuleTestBuilder extends RuleTestBuilder {

        public BlockRuleTestBuilder() {
            super();
            this.predicateType("minecraft:block_match");
        }

        public BlockRuleTestBuilder block(String blockID) {
            this.root.addProperty("block", blockID);
            return this;
        }
    }

    public static class BlockStateRuleTestBuilder extends RuleTestBuilder {

        public BlockStateRuleTestBuilder() {
            super();
            this.predicateType("minecraft:blockstate_match");
        }

        public BlockStateRuleTestBuilder blockState(Processor<StateDataBuilder> processor) {
            with("block_state", JsonObject::new, jsonObject -> processor.process(new StateDataBuilder()).buildTo(jsonObject));
            return this;
        }
    }

    public static class TagMatchRuleTestBuilder extends RuleTestBuilder {

        public TagMatchRuleTestBuilder() {
            super();
            this.predicateType("minecraft:tag_match");
        }

        public TagMatchRuleTestBuilder tag(String tagID) {
            this.root.addProperty("tag", tagID);
            return this;
        }
    }

    public static class RandomBlockMatchRuleTestBuilder extends RuleTestBuilder {

        public RandomBlockMatchRuleTestBuilder() {
            super();
            this.predicateType("minecraft:random_block_match");
        }

        public RandomBlockMatchRuleTestBuilder block(String blockID) {
            this.root.addProperty("block", blockID);
            return this;
        }

        public RandomBlockMatchRuleTestBuilder probability(float probability) {
            this.root.addProperty("probability", probability);
            return this;
        }
    }

    public static class RandomBlockStateMatchRuleTestBuilder extends RuleTestBuilder {

        public RandomBlockStateMatchRuleTestBuilder() {
            super();
            this.predicateType("minecraft:random_block_match");
        }

        public RandomBlockStateMatchRuleTestBuilder blockState(Processor<StateDataBuilder> processor) {
            with("block_state", JsonObject::new, jsonObject -> processor.process(new StateDataBuilder()).buildTo(jsonObject));
            return this;
        }

        public RandomBlockStateMatchRuleTestBuilder probability(float probability) {
            this.root.addProperty("probability", probability);
            return this;
        }
    }
}
