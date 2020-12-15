package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import net.minecraft.util.Identifier;

public final class DimensionTypeBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public DimensionTypeBuilder() {
        super(new JsonObject(), JsonResource::new);
    }

    /**
     * Overworld -> false
     * Nether -> true
     * End -> false
     * @param ultrawarm
     */
    public DimensionTypeBuilder ultrawarm(boolean ultrawarm) {
        root.addProperty("ultrawarm", ultrawarm);
        return this;
    }

    /**
     * Overworld -> true
     * Nether -> false
     * End -> false
     * @param natural
     */
    public DimensionTypeBuilder natural(boolean natural) {
        root.addProperty("natural", natural);
        return this;
    }

    /**
     * Overworld -> 1.0D
     * Nether -> 8.0D
     * End -> 1.0D
     * @param coordinate_scale
     */
    public DimensionTypeBuilder coordinate_scale(double coordinate_scale) {
        if (coordinate_scale < 0.00001) throw new IllegalArgumentException("Coordinate scale can't be higher than 0.00001D! Found " + coordinate_scale);
        if (coordinate_scale > 30000000) throw new IllegalArgumentException("Coordinate scale can't be higher than 30000000D! Found " + coordinate_scale);
        root.addProperty("coordinate_scale", coordinate_scale);
        return this;
    }

    /**
     * Overworld -> 0.0
     * Nether -> 0.1
     * End -> 0.0
     * @param ambientLight
     */
    public DimensionTypeBuilder ambientLight(float ambientLight) {
        root.addProperty("ambient_light", ambientLight);
        return this;
    }

    /**
     * Overworld -> true
     * Nether -> false
     * End -> false
     * @param hasSkylight
     */
    public DimensionTypeBuilder hasSkylight(boolean hasSkylight) {
        root.addProperty("has_skylight", hasSkylight);
        return this;
    }

    /**
     * Overworld -> false
     * Nether -> true
     * End -> false
     * @param hasCeiling
     */
    public DimensionTypeBuilder hasCeiling(boolean hasCeiling) {
        root.addProperty("has_ceiling", hasCeiling);
        return this;
    }

    /**
     * Overworld -> false
     * Nether -> false
     * End -> true
     * @param hasEnderDragonFight
     */
    public DimensionTypeBuilder hasEnderDragonFight(boolean hasEnderDragonFight) {
        root.addProperty("has_ender_dragon_fight", hasEnderDragonFight);
        return this;
    }

    /**
     * A block tag of which the blocks will not stop burning in the dimension.
     * Overworld -> minecraft:infiniburn_overworld
     * Nether -> minecraft:infiniburn_nether
     * End -> minecraft:infiniburn_end
     * @param infiniburn The block tag id.
     */
    public DimensionTypeBuilder infiniburn(Identifier infiniburn) {
        root.addProperty("infiniburn", infiniburn.toString());
        return this;
    }

    /**
     * The minimum height in which blocks can exist within this dimension.
     * @param minY
     */
    public DimensionTypeBuilder minY(int minY) {
        root.addProperty("min_y", minY);
        return this;
    }

    /**
     * The total height in which blocks can exist within this dimension. Max Y = Min Y + Height.
     * @param height
     */
    public DimensionTypeBuilder height(int height) {
        root.addProperty("height", height);
        return this;
    }

    /**
     * Overworld -> 256
     * Nether -> 128
     * End -> 256
     * @param logicalHeight
     */
    public DimensionTypeBuilder logicalHeight(int logicalHeight) {
        root.addProperty("logical_height", logicalHeight);
        return this;
    }

    /**
     * Set the fixed time of a dimension, do not set if you want a day-night cycle.
     * Nether -> 18000
     * End -> 6000
     * @param fixedTime Time of the days in ticks
     */
    public DimensionTypeBuilder fixedTime(long fixedTime) {
        root.addProperty("fixed_time", fixedTime);
        return this;
    }

    /**
     * Overworld -> true
     * Nether -> false
     * End -> true
     * @param hasRaids
     */
    public DimensionTypeBuilder hasRaids(boolean hasRaids) {
        root.addProperty("has_raids", hasRaids);
        return this;
    }

    /**
     * Overworld -> false
     * Nether -> true
     * End -> false
     * @param respawnAnchorWork
     */
    public DimensionTypeBuilder respawnAnchorWorks(boolean respawnAnchorWork) {
        root.addProperty("respawn_anchor_works", respawnAnchorWork);
        return this;
    }

    /**
     * Overworld -> true
     * Nether -> false
     * End -> false
     * @param bedWorks
     */
    public DimensionTypeBuilder bedWorks(boolean bedWorks) {
        root.addProperty("bed_works", bedWorks);
        return this;
    }

    /**
     * Overworld -> false
     * Nether -> true
     * End -> false
     * @param piglinSafe
     */
    public DimensionTypeBuilder piglinSafe(boolean piglinSafe) {
        root.addProperty("piglin_safe", piglinSafe);
        return this;
    }

    /**
     * Effects determine the sky effect of the dimension.
     *
     * Overworld -> minecraft:overworld
     * Nether -> minecraft:the_nether
     * End -> minecraft:the_end
     * @param effects thing
     */
    public DimensionTypeBuilder effects(String effects) {
        root.addProperty("effects", effects);
        return this;
    }
}
