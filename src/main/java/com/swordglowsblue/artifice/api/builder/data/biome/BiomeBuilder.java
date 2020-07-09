package com.swordglowsblue.artifice.api.builder.data.biome;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;

public class BiomeBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public BiomeBuilder() {
        super(new JsonObject(), JsonResource::new);
        this.root.add("carvers", new JsonObject());
        this.root.add("starts", new JsonArray());
        this.root.add("features", new JsonArray());
        for (GenerationStep.Feature step : GenerationStep.Feature.values()) {
            this.root.getAsJsonArray("features").add(new JsonArray());
        }
        this.root.add("spawn_costs", new JsonObject());
        this.root.add("spawners", new JsonObject());
        for (SpawnGroup spawnGroup : SpawnGroup.values()) {
            this.root.getAsJsonObject("spawners").add(spawnGroup.getName(), new JsonArray());
        }
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
    public BiomeBuilder skyColor(int sky_color) {
        this.root.addProperty("sky_color", sky_color);
        return this;
    }

    public BiomeBuilder parent(String parent) {
        this.root.addProperty("parent", parent);
        return this;
    }

    public BiomeBuilder surfaceBuilder(String surface_builder) {
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

    public BiomeBuilder addSpawnCosts(String entityID, Processor<SpawnDensityBuilder> spawnDensityBuilderProcessor) {
        with(entityID, JsonObject::new, spawnDensityBuilder -> spawnDensityBuilderProcessor.process(new SpawnDensityBuilder()).buildTo(spawnDensityBuilder));
        return this;
    }

    public BiomeBuilder addSpawnEntry(SpawnGroup spawnGroup, Processor<BiomeSpawnEntryBuilder> biomeSpawnEntryBuilderProcessor) {
        this.root.getAsJsonObject("spawners").get(spawnGroup.getName()).getAsJsonArray()
                .add(biomeSpawnEntryBuilderProcessor.process(new BiomeSpawnEntryBuilder()).buildTo(new JsonObject()));
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

    public BiomeBuilder addFeaturesbyStep(GenerationStep.Feature step, String... featureIDs) {
        for (String featureID : featureIDs)
            this.root.getAsJsonArray("features").get(step.ordinal()).getAsJsonArray().add(featureID);
        return this;
    }

    public BiomeBuilder addStructure(String structureID) {
        this.root.getAsJsonArray("starts").add(structureID);
        return this;
    }

    public static class SpawnDensityBuilder extends TypedJsonBuilder<JsonObject> {

        public SpawnDensityBuilder() {
            super(new JsonObject(), j->j);
        }

        public SpawnDensityBuilder energyBudget(double energy_budget) {
            this.root.addProperty("energy_budget", energy_budget);
            return this;
        }

        public SpawnDensityBuilder charge(double charge) {
            this.root.addProperty("charge", charge);
            return this;
        }
    }
}
