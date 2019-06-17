package com.swordglowsblue.artifice.impl.builder;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.impl.resource.JsonResource;
import com.swordglowsblue.artifice.impl.util.JsonBuilder;
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
