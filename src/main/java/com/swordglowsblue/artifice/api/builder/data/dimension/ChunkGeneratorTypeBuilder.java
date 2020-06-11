package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public final class ChunkGeneratorTypeBuilder extends TypedJsonBuilder<JsonObject> {
    public ChunkGeneratorTypeBuilder() {
        super(new JsonObject(), j->j);
    }

    public ChunkGeneratorTypeBuilder with(String type, Codec<? extends ChunkGenerator> codec) {
        this.root.addProperty("type", type);
        System.out.println(codec.toString());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "minecraft:fixed");
        jsonObject.addProperty("biome","minecraft:plains");
        this.root.add("biome_source", jsonObject);
        return this;
    }
}
