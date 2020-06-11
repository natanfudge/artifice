package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

import java.util.function.Function;

public class StructureManagerBuilder extends TypedJsonBuilder<JsonObject> {
    protected StructureManagerBuilder() {
        super(new JsonObject(), j->j);
        this.root.add("structures", new JsonObject());
    }

    public StructureManagerBuilder strongholdSettings(Processor<StrongholdSettingsBuilder> strongholdSettingsBuilder) {
        with("stronghold", JsonObject::new, jsonObject -> strongholdSettingsBuilder.process(new StrongholdSettingsBuilder()).buildTo(jsonObject));
        return this;
    }

    public StructureManagerBuilder addStructure(String structureId, Processor<StructureConfigBuilder> structureConfigBuilder) {
        this.with(this.root.getAsJsonObject("structures"), structureId, JsonObject::new, jsonObject -> structureConfigBuilder.process(new StructureConfigBuilder()).buildTo(jsonObject));
        return this;
    }


    public static class StrongholdSettingsBuilder extends TypedJsonBuilder<JsonObject> {

        protected StrongholdSettingsBuilder() {
            super(new JsonObject(), j->j);
        }

        public StrongholdSettingsBuilder distance(int distance) {
            this.root.addProperty("distance", distance);
            return this;
        }

        public StrongholdSettingsBuilder spread(int spread) {
            this.root.addProperty("spread", spread);
            return this;
        }

        public StrongholdSettingsBuilder count(int count) {
            this.root.addProperty("count", count);
            return this;
        }
    }

    public static class StructureConfigBuilder extends TypedJsonBuilder<JsonObject> {

        protected StructureConfigBuilder() {
            super(new JsonObject(), j->j);
        }

        public StructureConfigBuilder spacing(int spacing) {
            this.root.addProperty("spacing", spacing);
            return this;
        }

        public StructureConfigBuilder separation(int separation) {
            this.root.addProperty("separation", separation);
            return this;
        }

        public StructureConfigBuilder salt(int salt) {
            this.root.addProperty("salt", salt);
            return this;
        }
    }
}
