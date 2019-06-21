package com.swordglowsblue.artifice.api.builder.assets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.JsonArrayBuilder;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public final class BlockStateBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public BlockStateBuilder() { super(new JsonObject(), JsonResource::new); }

    public BlockStateBuilder variant(String name, Processor<Variant> settings) {
        root.remove("multipart");
        with("variants", JsonObject::new, variants -> with(variants, name, JsonObject::new, variant ->
            settings.process(new Variant(variant)).buildTo(variant)));
        return this;
    }

    public BlockStateBuilder weightedVariant(String name, Processor<Variant> settings) {
        root.remove("multipart");
        with("variants", JsonObject::new, variants -> with(variants, name, JsonArray::new, options ->
           options.add(settings.process(new Variant()).build())));
        return this;
    }

    public BlockStateBuilder multipartCase(Processor<Case> settings) {
        root.remove("variants");
        with("multipart", JsonArray::new, cases -> cases.add(settings.process(new Case()).build()));
        return this;
    }

    @Environment(EnvType.CLIENT)
    public static final class Variant extends TypedJsonBuilder<JsonObject> {
        private Variant() { super(new JsonObject(), j->j); }
        private Variant(JsonObject root) { super(root, j->j); }

        public Variant model(Identifier id) {
            root.addProperty("model", id.toString());
            return this;
        }

        public Variant rotationX(int x) {
            if(x % 90 != 0) throw new IllegalArgumentException("X rotation must be in increments of 90");
            root.addProperty("x", x);
            return this;
        }

        public Variant rotationY(int y) {
            if(y % 90 != 0) throw new IllegalArgumentException("Y rotation must be in increments of 90");
            root.addProperty("y", y);
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

    @Environment(EnvType.CLIENT)
    public static final class Case extends TypedJsonBuilder<JsonObject> {
        private Case() { super(new JsonObject(), j->j); }

        public Case when(String name, String state) {
            with("when", JsonObject::new, when -> {
                when.remove("OR");
                when.addProperty(name, state);
            });
            return this;
        }

        public Case whenAny(String name, String state) {
            with("when", JsonObject::new, when -> with(when, "OR", JsonArray::new, cases -> {
                when.entrySet().forEach(e -> { if(!e.getKey().equals("OR")) when.remove(e.getKey()); });
                cases.add(new JsonObjectBuilder().add(name, state).build());
            }));
            return this;
        }

        public Case apply(Processor<Variant> settings) {
            root.add("apply", settings.process(new Variant()).build());
            return this;
        }

        public Case weightedApply(Processor<Variant> settings) {
            with("apply", JsonArray::new, options -> options.add(settings.process(new Variant()).build()));
            return this;
        }
    }
}
