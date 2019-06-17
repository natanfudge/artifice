package com.swordglowsblue.artifice.impl.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.impl.resource.JsonResource;
import com.swordglowsblue.artifice.impl.util.JsonBuilder;
import com.swordglowsblue.artifice.impl.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class ModelBuilder extends JsonBuilder<JsonResource> {
    public ModelBuilder() { super(new JsonObject(), JsonResource::new); }

    public ModelBuilder parent(Identifier id) {
        root.addProperty("parent", id.toString());
        return this;
    }

    public ModelBuilder texture(String name, Identifier path) {
        with("textures", JsonObject::new, textures -> textures.addProperty(name, path.toString()));
        return this;
    }

    public ModelBuilder display(String name, Processor<Display> settings) {
        with("display", JsonObject::new, display ->
            settings.process(new Display(display)).buildTo(display)
        );
        return this;
    }

    public ModelBuilder element(Processor<ModelElementBuilder> settings) {
        with("elements", JsonArray::new, elements ->
           elements.add(settings.process(new ModelElementBuilder()).build())
        );
        return this;
    }

    public ModelBuilder ambientocclusion(boolean ambientocclusion) {
        this.root.addProperty("ambientocclusion", ambientocclusion);
        return this;
    }

    // TODO: Item model overrides

    public static class Display extends JsonBuilder<JsonObject> {
        private Display(JsonObject root) { super(root, j->j); }

        public Display rotation(int x, int y, int z) {
            root.add("rotation", arrayOf(x, y, z));
            return this;
        }

        public Display translation(int x, int y, int z) {
            root.add("translation", arrayOf(x, y, z));
            return this;
        }

        public Display scale(int x, int y, int z) {
            root.add("scale", arrayOf(x, y, z));
            return this;
        }
    }
}
