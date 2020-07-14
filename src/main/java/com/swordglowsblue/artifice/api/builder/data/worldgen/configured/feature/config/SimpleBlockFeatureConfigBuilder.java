package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class SimpleBlockFeatureConfigBuilder extends FeatureConfigBuilder {

    public SimpleBlockFeatureConfigBuilder() {
        super();
        this.root.add("place_on", new JsonArray());
        this.root.add("place_in", new JsonArray());
        this.root.add("place_under", new JsonArray());
    }

    public SimpleBlockFeatureConfigBuilder toPlace(Processor<StateDataBuilder> processor) {
        with("to_place", JsonObject::new, jsonObject -> processor.process(new StateDataBuilder()).buildTo(jsonObject));
        return this;
    }

    public SimpleBlockFeatureConfigBuilder addPlaceOn(Processor<StateDataBuilder> processor) {
        this.root.getAsJsonArray("place_on").add(processor.process(new StateDataBuilder()).buildTo(new JsonObject()));
        return this;
    }

    public SimpleBlockFeatureConfigBuilder addPlaceIn(Processor<StateDataBuilder> processor) {
        this.root.getAsJsonArray("place_in").add(processor.process(new StateDataBuilder()).buildTo(new JsonObject()));
        return this;
    }

    public SimpleBlockFeatureConfigBuilder addPlaceUnder(Processor<StateDataBuilder> processor) {
        this.root.getAsJsonArray("place_under").add(processor.process(new StateDataBuilder()).buildTo(new JsonObject()));
        return this;
    }
}
