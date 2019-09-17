package artificemc.artifice.api.builders.client;

import artificemc.artifice.api.builders.TypedJsonBuilder;
import artificemc.artifice.api.core.resource.JsonResource;
import artificemc.artifice.impl.core.util.IdUtils;

/**
 * Builder for a particle definition ({@code namespace:particles/particleid.json}).
 */
public final class ParticleBuilder extends TypedJsonBuilder<JsonResource, ParticleBuilder> {
    public ParticleBuilder() { super(JsonResource::new); }

    /**
     * Add a texture to this particle.
     * Calling this multiple times will add to the list instead of overwriting.
     * @param id The texure ID ({@code namespace:textureid}).
     * @return this
     */
    public ParticleBuilder texture(String id) {
        IdUtils.validateIdentifier(id);
        withArray("textures", textures -> textures.add(id));
        return this;
    }
}
