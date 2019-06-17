package com.swordglowsblue.artifice.impl.resource;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.impl.util.JsonBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class TranslationResource extends JsonResource {
    public TranslationResource(JsonElement root) { super(root); }

    public static final class Builder extends JsonBuilder<Builder, TranslationResource> {
        public Builder() { super(new JsonObject(), TranslationResource::new); }

        public Builder entry(String key, String translation) {
            root.addProperty(key, translation);
            return this;
        }
    }
}
