package com.swordglowsblue.artifice.api.builder.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.impl.util.Processor;
import net.minecraft.util.Identifier;

public class LootTableBuilder extends TypedJsonBuilder<JsonResource> {
    public LootTableBuilder() { super(new JsonObject(), JsonResource::new); }

    public LootTableBuilder type(Identifier id) {
        root.addProperty("type", id.toString());
        return this;
    }

    public LootTableBuilder pool(Processor<Pool> settings) {
        with("pools", JsonArray::new, pools ->
            pools.add(settings.process(new Pool()).build())
        );
        return this;
    }

    public static final class Pool extends TypedJsonBuilder<JsonObject> {
        private Pool() { super(new JsonObject(), j->j); }

        public Pool condition(Identifier id, Processor<JsonObjectBuilder> settings) {
            with("conditions", JsonArray::new, conditions -> {
                JsonObject cond = new JsonObject();
                cond.addProperty("condition", id.toString());
                conditions.add(settings.process(new JsonObjectBuilder(cond)).build());
            });
            return this;
        }

        public Pool rolls(int rolls) {
            root.addProperty("rolls", rolls);
            return this;
        }

        public Pool rolls(int min, int max) {
            JsonObject rolls = new JsonObject();
            rolls.addProperty("min", min);
            rolls.addProperty("max", max);
            root.add("rolls", rolls);
            return this;
        }

        public Pool bonusRolls(float rolls) {
            root.addProperty("bonus_rolls", rolls);
            return this;
        }

        public Pool bonusRolls(float min, float max) {
            JsonObject rolls = new JsonObject();
            rolls.addProperty("min", min);
            rolls.addProperty("max", max);
            root.add("bonus_rolls", rolls);
            return this;
        }

        public static final class Entry extends TypedJsonBuilder<JsonObject> {
            private Entry() { super(new JsonObject(), j->j); }

            public Entry type(Identifier id) {
                root.addProperty("type", id.toString());
                return this;
            }

            public Entry name(Identifier id) {
                root.addProperty("name", id.toString());
                return this;
            }

            public Entry child(Processor<Entry> settings) {
                with("children", JsonArray::new, children ->
                   children.add(settings.process(new Entry()).build())
                );
                return this;
            }

            public Entry expand(boolean expand) {
                root.addProperty("expand", expand);
                return this;
            }

            public Entry function(Identifier id, Processor<Function> settings) {
                with("functions", JsonArray::new, functions -> {
                    JsonObject func = new JsonObject();
                    func.addProperty("function", id.toString());
                    functions.add(settings.process(new Function(func)).build());
                });
                return this;
            }

            public static final class Function extends JsonObjectBuilder {
                private Function(JsonObject func) { super(func); }

                public Function condition(Identifier id, Processor<JsonObjectBuilder> settings) {
                    with("conditions", JsonArray::new, conditions -> {
                        JsonObject cond = new JsonObject();
                        cond.addProperty("condition", id.toString());
                        conditions.add(settings.process(new JsonObjectBuilder(cond)).build());
                    });
                    return this;
                }
            }
        }
    }
}
