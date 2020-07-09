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

    /**
     * Build stronghold settings.
     * @param strongholdSettingsBuilder
     * @return
     */
    public StructureManagerBuilder strongholdSettings(Processor<StrongholdSettingsBuilder> strongholdSettingsBuilder) {
        with("stronghold", JsonObject::new, jsonObject -> strongholdSettingsBuilder.process(new StrongholdSettingsBuilder()).buildTo(jsonObject));
        return this;
    }

    /**
     * Add structure.
     * @param structureId
     * @param structureConfigBuilder
     * @return
     */
    public StructureManagerBuilder addStructure(String structureId, Processor<StructureConfigBuilder> structureConfigBuilder) {
        this.with(this.root.getAsJsonObject("structures"), structureId, JsonObject::new, jsonObject -> structureConfigBuilder.process(new StructureConfigBuilder()).buildTo(jsonObject));
        return this;
    }


    public static class StrongholdSettingsBuilder extends TypedJsonBuilder<JsonObject> {

        protected StrongholdSettingsBuilder() {
            super(new JsonObject(), j->j);
        }

        /**
         * @param distance
         * @return
         */
        public StrongholdSettingsBuilder distance(int distance) {
            try {
                if (distance > 1023) throw new Throwable("Distance can't be higher than 1023! Found " + distance);
                if (distance < 0) throw new Throwable("Distance can't be smaller than 0! Found " + distance);
                this.root.addProperty("distance", distance);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return this;
        }

        /**
         * @param spread
         * @return
         */
        public StrongholdSettingsBuilder spread(int spread) {
            try {
                if (spread > 1023) throw new Throwable("Spread can't be higher than 1023! Found " + spread);
                if (spread < 0) throw new Throwable("Spread can't be smaller than 0! Found " + spread);
                this.root.addProperty("spread", spread);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return this;
        }

        /**
         * Set the number of stronghold in the dimension.
         * @param count
         * @return
         */
        public StrongholdSettingsBuilder count(int count) {
            try {
                if (count > 4095) throw new Throwable("Count can't be higher than 4095! Found " + count);
                if (count < 1) throw new Throwable("Count can't be smaller than 1! Found " + count);
                this.root.addProperty("count", count);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return this;
        }
    }

    public static class StructureConfigBuilder extends TypedJsonBuilder<JsonObject> {

        protected StructureConfigBuilder() {
            super(new JsonObject(), j->j);
        }

        public StructureConfigBuilder spacing(int spacing) {
            try {
                if (spacing > 4096) throw new Throwable("Count can't be higher than 4096! Found " + spacing);
                if (spacing < 0) throw new Throwable("Count can't be smaller than 0! Found " + spacing);
                this.root.addProperty("spacing", spacing);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return this;
        }

        public StructureConfigBuilder separation(int separation) {
            try {
                if (separation > 4096) throw new Throwable("Count can't be higher than 4096! Found " + separation);
                if (separation < 0) throw new Throwable("Count can't be smaller than 0! Found " + separation);
                this.root.addProperty("separation", separation);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return this;
        }

        public StructureConfigBuilder salt(int salt) {
            try {
                if (salt < 0) throw new Throwable("Count can't be smaller than 0! Found " + salt);
                this.root.addProperty("salt", salt);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return this;
        }
    }
}
