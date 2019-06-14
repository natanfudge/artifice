package com.swordglowsblue.artifice.impl.resource_types;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModelElementBuilder {
    private final JsonObject element;
    ModelElementBuilder() { this.element = new JsonObject(); }

    public ModelElementBuilder from(float x, float y, float z) {
        JsonArray from = new JsonArray();
        from.add(MathHelper.clamp(x, -16, 32));
        from.add(MathHelper.clamp(y, -16, 32));
        from.add(MathHelper.clamp(z, -16, 32));
        element.add("from", from);
        return this;
    }

    public ModelElementBuilder to(float x, float y, float z) {
        JsonArray to = new JsonArray();
        to.add(MathHelper.clamp(x, -16, 32));
        to.add(MathHelper.clamp(y, -16, 32));
        to.add(MathHelper.clamp(z, -16, 32));
        element.add("to", to);
        return this;
    }

    public ModelElementBuilder rotation(Consumer<RotationBuilder> settings) {
        with("rotation", JsonObject::new, rotation -> {
            RotationBuilder builder = new RotationBuilder(rotation);
            settings.accept(builder);
            JsonObject newRotation = builder.build();
            newRotation.entrySet().forEach(e -> rotation.add(e.getKey(), e.getValue()));
        });
        return this;
    }

    public ModelElementBuilder shade(boolean shade) {
        element.addProperty("shade", shade);
        return this;
    }

    public ModelElementBuilder face(Direction side, Consumer<FaceBuilder> settings) {
        with("faces", JsonObject::new, faces -> with(side.getName(), JsonObject::new, face -> {
            FaceBuilder builder = new FaceBuilder(face);
            settings.accept(builder);
            JsonObject newFace = builder.build();


        }));
        return this;
    }

    JsonObject build() { return element; }

    @SuppressWarnings("unchecked")
    private <T extends JsonElement> void with(String name, Supplier<T> ctor, Consumer<T> run) {
        T prop = element.has(name) ? (T)element.get(name) : ctor.get();
        run.accept(prop);
        element.add(name, prop);
    }

    public static class RotationBuilder {
        private final JsonObject rotation;
        RotationBuilder(JsonObject rotation) { this.rotation = rotation; }

        public RotationBuilder origin(float x, float y, float z) {
            JsonArray origin = new JsonArray();
            origin.add(MathHelper.clamp(x, -16, 32));
            origin.add(MathHelper.clamp(y, -16, 32));
            origin.add(MathHelper.clamp(z, -16, 32));
            rotation.add("origin", origin);
            return this;
        }

        public RotationBuilder axis(Direction.Axis axis) {
            rotation.addProperty("axis", axis.getName());
            return this;
        }

        public RotationBuilder angle(float angle) {
            rotation.addProperty("angle", (MathHelper.clamp(angle, -45f, 45f) % 22.5f) * 22.5f);
            return this;
        }

        public RotationBuilder rescale(boolean rescale) {
            rotation.addProperty("rescale", rescale);
            return this;
        }

        JsonObject build() { return rotation; }
    }

    public static class FaceBuilder {
        private final JsonObject face;
        FaceBuilder(JsonObject face) { this.face = face; }

        public FaceBuilder uv(float x1, float x2, float y1, float y2) {
            JsonArray uv = new JsonArray();
            uv.add(MathHelper.clamp(x1, -16, 32));
            uv.add(MathHelper.clamp(x2, -16, 32));
            uv.add(MathHelper.clamp(y1, -16, 32));
            uv.add(MathHelper.clamp(y2, -16, 32));
            face.add("uv", uv);
            return this;
        }

        public FaceBuilder texture(String varName) {
            face.addProperty("texture", "#"+varName);
            return this;
        }

        public FaceBuilder texture(Identifier path) {
            face.addProperty("texture", path.toString());
            return this;
        }

        public FaceBuilder cullface(Direction side) {
            face.addProperty("cullface", side.getName());
            return this;
        }

        public FaceBuilder rotation(FaceRotation rotation) {
            face.addProperty("rotation", rotation.degrees);
            return this;
        }

        public FaceBuilder tintindex(int tintindex) {
            face.addProperty("tintindex", tintindex);
            return this;
        }

        JsonObject build() { return face; }
    }

    public enum FaceRotation {
        ROTATE_0(0), ROTATE_90(90), ROTATE_180(180), ROTATE_270(270);
        public final int degrees;
        FaceRotation(int degrees) { this.degrees = degrees; }
    }
}
