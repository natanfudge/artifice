package com.swordglowsblue.artifice.impl.resource_types;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.ArtificeResource;
import com.swordglowsblue.artifice.impl.util.JsonBuilder;
import com.swordglowsblue.artifice.impl.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ArtificeModelResource implements ArtificeResource {
    private final JsonObject model;
    private ArtificeModelResource(JsonObject model) { this.model = model; }
    public JsonObject toJson() { return model; }

    public static class BlockBuilder extends ArtificeModelResource.Builder<BlockBuilder> {
        public BlockBuilder ambientocclusion(boolean ambientocclusion) {
            this.root.addProperty("ambientocclusion", ambientocclusion);
            return this;
        }
    }

    public static class ItemBuilder extends ArtificeModelResource.Builder<ItemBuilder> {
        // TODO: Model overrides
    }

    @SuppressWarnings("unchecked")
    static abstract class Builder<T extends Builder<T>> extends JsonBuilder<T, ArtificeModelResource> {
        Builder() { super(new JsonObject(), ArtificeModelResource::new); }

        public T parent(Identifier id) {
            root.addProperty("parent", id.toString());
            return (T)this;
        }

        public T texture(String name, Identifier path) {
            with("textures", JsonObject::new, textures -> textures.addProperty(name, path.toString()));
            return (T)this;
        }

        public T display(String name, Processor<DisplayBuilder> settings) {
            with("display", JsonObject::new, display -> {
                DisplayBuilder builder = new DisplayBuilder(display);
                settings.process(builder).buildTo(display);
            });
            return (T)this;
        }

        public T element(Processor<ModelElementBuilder> settings) {
            with("elements", JsonArray::new, elements -> {
               ModelElementBuilder builder = new ModelElementBuilder();
               elements.add(settings.process(builder).build());
            });
            return (T)this;
        }
    }

    public static class DisplayBuilder extends JsonBuilder<DisplayBuilder, JsonObject> {
        DisplayBuilder(JsonObject root) { super(root, j->j); }

        public DisplayBuilder rotation(int x, int y, int z) {
            root.add("rotation", arrayOf(x, y, z));
            return this;
        }

        public DisplayBuilder translation(int x, int y, int z) {
            root.add("translation", arrayOf(x, y, z));
            return this;
        }

        public DisplayBuilder scale(int x, int y, int z) {
            root.add("scale", arrayOf(x, y, z));
            return this;
        }
    }
}
