package com.swordglowsblue.artifice.impl.resource;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.impl.util.JsonBuilder;
import com.swordglowsblue.artifice.impl.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class ModelElementBuilder extends JsonBuilder<ModelElementBuilder, JsonObject> {
    ModelElementBuilder() { super(new JsonObject(), j->j); }

    public ModelElementBuilder from(float x, float y, float z) {
        root.add("from", arrayOf(
            MathHelper.clamp(x, -16, 32),
            MathHelper.clamp(y, -16, 32),
            MathHelper.clamp(z, -16, 32)
        ));
        return this;
    }

    public ModelElementBuilder to(float x, float y, float z) {
        root.add("to", arrayOf(
            MathHelper.clamp(x, -16, 32),
            MathHelper.clamp(y, -16, 32),
            MathHelper.clamp(z, -16, 32)
        ));
        return this;
    }

    public ModelElementBuilder rotation(Processor<RotationBuilder> settings) {
        with("rotation", JsonObject::new, rotation -> {
            RotationBuilder builder = new RotationBuilder(rotation);
            settings.process(builder).buildTo(rotation);
        });
        return this;
    }

    public ModelElementBuilder shade(boolean shade) {
        root.addProperty("shade", shade);
        return this;
    }

    public ModelElementBuilder face(Direction side, Processor<FaceBuilder> settings) {
        with("faces", JsonObject::new, faces -> with(faces, side.getName(), JsonObject::new, face -> {
            FaceBuilder builder = new FaceBuilder(face);
            settings.process(builder).buildTo(face);
        }));
        return this;
    }

    public static class RotationBuilder extends JsonBuilder<RotationBuilder, JsonObject> {
        RotationBuilder(JsonObject root) { super(root, j->j); }

        public RotationBuilder origin(float x, float y, float z) {
            root.add("origin", arrayOf(
                MathHelper.clamp(x, -16, 32),
                MathHelper.clamp(y, -16, 32),
                MathHelper.clamp(z, -16, 32)
            ));
            return this;
        }

        public RotationBuilder axis(Direction.Axis axis) {
            root.addProperty("axis", axis.getName());
            return this;
        }

        public RotationBuilder angle(float angle) {
            root.addProperty("angle", MathHelper.clamp(angle, -45f, 45f) % 22.5f * 22.5f);
            return this;
        }

        public RotationBuilder rescale(boolean rescale) {
            root.addProperty("rescale", rescale);
            return this;
        }
    }

    public static class FaceBuilder extends JsonBuilder<FaceBuilder, JsonObject> {
        FaceBuilder(JsonObject root) { super(root, j->j); }

        public FaceBuilder uv(float x1, float x2, float y1, float y2) {
            root.add("uv", arrayOf(
                MathHelper.clamp(x1, -16, 32),
                MathHelper.clamp(x2, -16, 32),
                MathHelper.clamp(y1, -16, 32),
                MathHelper.clamp(y2, -16, 32)
            ));
            return this;
        }

        public FaceBuilder texture(String varName) {
            root.addProperty("texture", "#"+varName);
            return this;
        }

        public FaceBuilder texture(Identifier path) {
            root.addProperty("texture", path.toString());
            return this;
        }

        public FaceBuilder cullface(Direction side) {
            root.addProperty("cullface", side.getName());
            return this;
        }

        public FaceBuilder rotation(int rotation) {
            root.addProperty("rotation", MathHelper.clamp(rotation, 0, 270) % 90 * 90);
            return this;
        }

        public FaceBuilder tintindex(int tintindex) {
            root.addProperty("tintindex", tintindex);
            return this;
        }
    }
}
