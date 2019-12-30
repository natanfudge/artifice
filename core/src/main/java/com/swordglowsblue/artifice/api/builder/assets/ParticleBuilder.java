package com.swordglowsblue.artifice.api.builder.assets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

/**
 * Builder for a particle definition ({@code namespace:particles/particleid.json}).
 */
@Environment(EnvType.CLIENT)
public final class ParticleBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public ParticleBuilder() { super(new JsonObject(), JsonResource::new); }

    /**
     * Add a texture to this particle.
     * Calling this multiple times will add to the list instead of overwriting.
     * @param id The texure ID ({@code namespace:textureid}).
     * @return this
     */
    public ParticleBuilder texture(Identifier id) {
        with("textures", JsonArray::new, textures -> textures.add(id.toString()));
        return this;
    }
}
