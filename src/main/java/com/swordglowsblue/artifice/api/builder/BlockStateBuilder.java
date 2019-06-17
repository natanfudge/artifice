package com.swordglowsblue.artifice.api.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.impl.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class BlockStateBuilder extends JsonBuilder<JsonResource> {
    public BlockStateBuilder() { super(new JsonObject(), JsonResource::new); }

    public BlockStateBuilder variant(String name, Processor<Variant> settings) {
        root.remove("multipart");
        with("variants", JsonObject::new, variants -> with(variants, name, JsonObject::new, variant ->
            settings.process(new Variant(variant)).buildTo(variant)
        ));
        return this;
    }

    public BlockStateBuilder weightedVariant(String name, Processor<Variant> settings) {
        root.remove("multipart");
        with("variants", JsonObject::new, variants -> with(variants, name, JsonArray::new, options ->
           options.add(settings.process(new Variant()).build())
        ));
        return this;
    }

    public BlockStateBuilder multipartCase(Processor<Case> settings) {
        root.remove("variants");
        with("multipart", JsonArray::new, cases ->
            cases.add(settings.process(new Case()).build())
        );
        return this;
    }

    public static final class Variant extends JsonBuilder<JsonObject> {
        private Variant() { super(new JsonObject(), j->j); }
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

    public static final class Case extends JsonBuilder<JsonObject> {
        private Case() { super(new JsonObject(), j->j); }

        public Case when(String name, String state) {
            root.addProperty(name, state);
            return this;
        }

        public Case whenAny(String name, String state) {
            JsonElement or = root.get("OR");
            root.entrySet().forEach(e -> root.remove(e.getKey()));
            root.add("OR", or);

            JsonObject obj = new JsonObject();
            obj.addProperty(name, state);
            with("OR", JsonArray::new, cases -> cases.add(obj));

            return this;
        }

        public Case apply(Processor<Variant> settings) {
            root.add("apply", settings.process(new Variant()).build());
            return this;
        }

        public Case weightedApply(Processor<Variant> settings) {
            with("apply", JsonArray::new, options ->
                options.add(settings.process(new Variant()).build())
            );
            return this;
        }
    }
}
