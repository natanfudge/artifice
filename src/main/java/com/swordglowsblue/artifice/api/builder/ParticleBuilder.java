package com.swordglowsblue.artifice.api.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import net.minecraft.util.Identifier;

public class ParticleBuilder extends JsonBuilder<JsonResource> {
    public ParticleBuilder() { super(new JsonObject(), JsonResource::new); }

    public ParticleBuilder texture(Identifier id) {
        with("textures", JsonArray::new, textures -> textures.add(id.toString()));
        return this;
    }
}
