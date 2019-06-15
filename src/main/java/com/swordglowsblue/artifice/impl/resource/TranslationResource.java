package com.swordglowsblue.artifice.impl.resource;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.impl.util.JsonBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class TranslationResource extends JsonResource {
    private final String languageKey;
//    private final Supplier<LanguageDefinition> languageSupplier;

//    public LanguageDefinition getLanguage() {
//        LanguageDefinition lang = languageSupplier.get();
//        if(lang == null) throw new IllegalArgumentException("Tried to register translation file for nonexistent language");
//        return lang;
//    }

    public TranslationResource(String languageKey, JsonElement root) {
        super(root);
        this.languageKey = languageKey;
//        this.languageSupplier = () -> {
//            if(MinecraftClient.getInstance().getLanguageManager() == null) return null;
//            return MinecraftClient.getInstance().getLanguageManager().getLanguage(languageKey);
//        };
    }

//    public TranslationResource(LanguageDefinition language, JsonElement root) {
//        super(root);
//        this.languageKey = language.getCode();
//        this.languageSupplier = () -> language;
//    }

    public static final class Builder extends JsonBuilder<Builder, TranslationResource> {
        public Builder(String languageKey) { super(new JsonObject(), j -> new TranslationResource(languageKey, j)); }
//        public Builder(LanguageDefinition language) { super(new JsonObject(), j -> new TranslationResource(language, j)); }

        public Builder entry(String key, String translation) {
            root.addProperty(key, translation);
            return this;
        }
    }
}
