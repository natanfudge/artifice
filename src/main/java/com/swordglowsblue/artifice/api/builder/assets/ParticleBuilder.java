package com.swordglowsblue.artifice.api.builder.assets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class ParticleBuilder extends TypedJsonBuilder<JsonResource> {
    public ParticleBuilder() { super(new JsonObject(), JsonResource::new); }

    public ParticleBuilder texture(Identifier id) {
        with("textures", JsonArray::new, textures -> textures.add(id.toString()));
        return this;
    }
}
