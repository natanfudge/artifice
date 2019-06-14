package com.swordglowsblue.artifice.impl.resource_types;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.ArtificeResource;
import com.swordglowsblue.artifice.impl.util.JsonBuilder;
import com.swordglowsblue.artifice.impl.util.Processor;
import net.minecraft.util.Identifier;

public class ArtificeBlockStateResource implements ArtificeResource {
    private final JsonObject state;
    private ArtificeBlockStateResource(JsonObject state) { this.state = state; }
    public JsonObject toJson() { return state; }

    public static final class Builder extends JsonBuilder<Builder, ArtificeBlockStateResource> {
        public Builder() { super(new JsonObject(), ArtificeBlockStateResource::new); }

        public Builder variant(String name, Processor<VariantBuilder> settings) {
            with("variants", JsonObject::new, variants -> with(variants, name, JsonObject::new, variant -> {
                VariantBuilder builder = new VariantBuilder(variant);
                settings.process(builder).buildTo(variant);
            }));
            return this;
        }

        public Builder weightedVariant(String name, Processor<VariantBuilder> settings) {
            with("variants", JsonObject::new, variants -> with(variants, name, JsonArray::new, options -> {
               VariantBuilder builder = new VariantBuilder(new JsonObject());
               options.add(settings.process(builder).build());
            }));
            return this;
        }

        // TODO: multipart
    }

    public static class VariantBuilder extends JsonBuilder<VariantBuilder, JsonObject> {
        VariantBuilder(JsonObject root) { super(root, j->j); }

        public VariantBuilder model(Identifier id) {
            root.addProperty("model", id.toString());
            return this;
        }

        public VariantBuilder rotationX(int x) {
            root.addProperty("x", x % 90 * 90);
            return this;
        }

        public VariantBuilder rotationY(int y) {
            root.addProperty("y", y % 90 * 90);
            return this;
        }

        public VariantBuilder uvlock(boolean uvlock) {
            root.addProperty("uvlock", uvlock);
            return this;
        }

        public VariantBuilder weight(int weight) {
            root.addProperty("weight", weight);
            return this;
        }
    }
}
