package com.swordglowsblue.artifice.api.builder;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class TranslationBuilder extends JsonBuilder<JsonResource> {
    public TranslationBuilder() { super(new JsonObject(), JsonResource::new); }

    public TranslationBuilder entry(String key, String translation) {
        root.addProperty(key, translation);
        return this;
    }
}
