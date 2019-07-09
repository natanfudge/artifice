package com.swordglowsblue.artifice.api.builder.assets;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * Builder for a translation file ({@code namespace:lang/language_code.json}).
 * @see <a href="https://minecraft.gamepedia.com/Resource_pack#Language" target="_blank">Minecraft Wiki</a>
 */
@Environment(EnvType.CLIENT)
public final class TranslationBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public TranslationBuilder() { super(new JsonObject(), JsonResource::new); }

    /**
     * Add a translation entry.
     * @param key The translation key (e.g. {@code block.example.example_block}).
     * @param translation The translated string (e.g. {@code Example Block}).
     * @return this
     */
    public TranslationBuilder entry(String key, String translation) {
        root.addProperty(key, translation);
        return this;
    }
}
