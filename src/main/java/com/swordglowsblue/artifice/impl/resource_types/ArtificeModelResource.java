package com.swordglowsblue.artifice.impl.resource_types;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.ArtificeResource;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ArtificeModelResource implements ArtificeResource {
    private final JsonObject model;
    private ArtificeModelResource(JsonObject model) { this.model = model; }
    public JsonObject toJson() { return model; }

    @SuppressWarnings("unchecked")
    static abstract class Builder<T extends Builder<T>> {
        protected final JsonObject model = new JsonObject();

        public T parent(Identifier id) {
            model.addProperty("parent", id.toString());
            return (T)this;
        }

        public T texture(String name, Identifier path) {
            with("textures", JsonObject::new, textures -> textures.addProperty(name, path.toString()));
            return (T)this;
        }

        public T display(String name, Consumer<ModelDisplayBuilder> settings) {
            with("display", JsonObject::new, display -> {
                ModelDisplayBuilder builder = new ModelDisplayBuilder(display);
                settings.accept(builder);
                JsonObject newDisplay = builder.build();
                newDisplay.entrySet().forEach(e -> display.add(e.getKey(), e.getValue()));
            });
            return (T)this;
        }

        public T element(Consumer<ModelElementBuilder> settings) {
            with("elements", JsonArray::new, elements -> {
               ModelElementBuilder builder = new ModelElementBuilder();
               settings.accept(builder);
               elements.add(builder.build());
            });
            return (T)this;
        }

        public ArtificeModelResource build() {
            JsonObject modelCopy = new JsonObject();
            model.entrySet().forEach(entry -> modelCopy.add(entry.getKey(), entry.getValue()));
            return new ArtificeModelResource(modelCopy);
        }

        @SuppressWarnings("unchecked")
        protected <T extends JsonElement> void with(String name, Supplier<T> ctor, Consumer<T> run) {
            T element = model.has(name) ? (T)model.get(name) : ctor.get();
            run.accept(element);
            model.add(name, element);
        }
    }
}
