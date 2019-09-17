package artificemc.artifice.api.builders.client;

import artificemc.artifice.api.builders.TypedJsonBuilder;
import artificemc.artifice.api.core.resource.JsonResource;

/**
 * Builder for a translation file ({@code namespace:lang/language_code.json}).
 * @see <a href="https://minecraft.gamepedia.com/Resource_pack#Language" target="_blank">Minecraft Wiki</a>
 */
public final class TranslationBuilder extends TypedJsonBuilder<JsonResource, TranslationBuilder> {
    public TranslationBuilder() { super(JsonResource::new); }

    /**
     * Add a translation entry.
     * @param key The translation key (e.g. {@code block.example.example_block}).
     * @param translation The translated string (e.g. {@code Example Block}).
     * @return this
     */
    public TranslationBuilder entry(String key, String translation) {
        this.set(key, translation);
        return this;
    }
}
