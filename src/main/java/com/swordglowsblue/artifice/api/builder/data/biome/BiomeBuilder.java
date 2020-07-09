package com.swordglowsblue.artifice.api.builder.data.biome;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;

public class BiomeBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public BiomeBuilder() {
        super(new JsonObject(), JsonResource::new);
        this.root.add("carvers", new JsonObject());
    }

    public BiomeBuilder depth(float depth) {
        this.root.addProperty("depth", depth);
        return this;
    }

    public BiomeBuilder scale(float scale) {
        this.root.addProperty("scale", scale);
        return this;
    }

    public BiomeBuilder temperature(float temperature) {
        this.root.addProperty("temperature", temperature);
        return this;
    }

    public BiomeBuilder downfall(float downfall) {
        this.root.addProperty("downfall", downfall);
        return this;
    }


    /**
     * @param sky_color RGB value.
     * @return BiomeBuilder
     */
    public BiomeBuilder sky_color(int sky_color) {
        this.root.addProperty("sky_color", sky_color);
        return this;
    }

    public BiomeBuilder parent(String parent) {
        this.root.addProperty("parent", parent);
        return this;
    }

    public BiomeBuilder surface_builder(String surface_builder) {
        this.root.addProperty("surface_builder", surface_builder);
        return this;
    }

    public BiomeBuilder precipitation(Biome.Precipitation precipitation) {
        this.root.addProperty("precipitation", precipitation.getName());
        return this;
    }

    public BiomeBuilder category(Biome.Category category) {
        this.root.addProperty("category", category.getName());
        return this;
    }

    public BiomeBuilder effects(Processor<BiomeEffectsBuilder> biomeEffectsBuilder) {
        with("effects", JsonObject::new, biomeEffects -> biomeEffectsBuilder.process(new BiomeEffectsBuilder()).buildTo(biomeEffects));
        return this;
    }

    private BiomeBuilder addCarver(GenerationStep.Carver carver, String[] configuredCaverIDs) {
        for (String configuredCaverID : configuredCaverIDs)
            this.root.getAsJsonObject("carvers").getAsJsonArray(carver.getName()).add(configuredCaverID);
        return this;
    }

    public BiomeBuilder addAirCarvers(String... configuredCarverIds) {
        this.root.getAsJsonObject("carvers").add(GenerationStep.Carver.AIR.getName(), new JsonArray());
        this.addCarver(GenerationStep.Carver.AIR, configuredCarverIds);
        return this;
    }

    public BiomeBuilder addLiquidCarvers(String... configuredCarverIds) {
        this.root.getAsJsonObject("carvers").add(GenerationStep.Carver.LIQUID.getName(), new JsonArray());
        this.addCarver(GenerationStep.Carver.LIQUID, configuredCarverIds);
        return this;
    }
}
