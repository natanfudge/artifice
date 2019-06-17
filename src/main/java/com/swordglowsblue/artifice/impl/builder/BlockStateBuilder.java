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
public final class BlockStateBuilder extends JsonBuilder<JsonResource> {
    public BlockStateBuilder() { super(new JsonObject(), JsonResource::new); }

    public BlockStateBuilder variant(String name, Processor<Variant> settings) {
        with("variants", JsonObject::new, variants -> with(variants, name, JsonObject::new, variant ->
            settings.process(new Variant(variant)).buildTo(variant)
        ));
        return this;
    }

    public BlockStateBuilder weightedVariant(String name, Processor<Variant> settings) {
        with("variants", JsonObject::new, variants -> with(variants, name, JsonArray::new, options ->
           options.add(settings.process(new Variant(new JsonObject())).build())
        ));
        return this;
    }

    // TODO: multipart

    public static final class Variant extends JsonBuilder<JsonObject> {
        private Variant(JsonObject root) { super(root, j->j); }

        public Variant model(Identifier id) {
            root.addProperty("model", id.toString());
            return this;
        }

        public Variant rotationX(int x) {
            root.addProperty("x", x % 90 * 90);
            return this;
        }

        public Variant rotationY(int y) {
            root.addProperty("y", y % 90 * 90);
            return this;
        }

        public Variant uvlock(boolean uvlock) {
            root.addProperty("uvlock", uvlock);
            return this;
        }

        public Variant weight(int weight) {
            root.addProperty("weight", weight);
            return this;
        }
    }
}
