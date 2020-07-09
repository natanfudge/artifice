package com.swordglowsblue.artifice.api.builder.data.worldgen;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;

public class CarverBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public CarverBuilder() {
        super(new JsonObject(), JsonResource::new);
    }

    /**
     * @param id ID of an existing carver.
     * @return this
     */
    public CarverBuilder name(String id) {
        this.root.addProperty("name", id);
        return this;
    }

    public CarverBuilder probability(float probability) {
        try {
            if (probability > 1.0F) throw new Throwable("Probability can't be higher than 1.0F! Found " + probability);
            if (probability < 0.0F) throw new Throwable("Probability can't be smaller than 0.0F! Found " + probability);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("probability", probability);
            this.root.add("config", jsonObject);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return this;
    }
}
