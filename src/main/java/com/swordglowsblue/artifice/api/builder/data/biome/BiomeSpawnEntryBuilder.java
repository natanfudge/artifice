package com.swordglowsblue.artifice.api.builder.data.biome;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;

public class BiomeSpawnEntryBuilder extends TypedJsonBuilder<JsonObject> {

    public BiomeSpawnEntryBuilder() {
        super(new JsonObject(), j->j);
    }

    public BiomeSpawnEntryBuilder entityID(String entityID) {
        this.root.addProperty("type", entityID);
        return this;
    }

    public BiomeSpawnEntryBuilder weight(int weight) {
        this.root.addProperty("weight", weight);
        return this;
    }

    public BiomeSpawnEntryBuilder minCount(int minCount) {
        this.root.addProperty("minCount", minCount);
        return this;
    }

    public BiomeSpawnEntryBuilder maxCount(int maxCount) {
        this.root.addProperty("maxCount", maxCount);
        return this;
    }
}
