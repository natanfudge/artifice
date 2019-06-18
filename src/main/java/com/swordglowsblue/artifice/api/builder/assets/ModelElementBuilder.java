package com.swordglowsblue.artifice.api.builder.assets;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public final class ModelElementBuilder extends TypedJsonBuilder<JsonObject> {
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

    public ModelElementBuilder rotation(Processor<Rotation> settings) {
        with("rotation", JsonObject::new, rotation -> {
            Rotation builder = new Rotation(rotation);
            settings.process(builder).buildTo(rotation);
        });
        return this;
    }

    public ModelElementBuilder shade(boolean shade) {
        root.addProperty("shade", shade);
        return this;
    }

    public ModelElementBuilder face(Direction side, Processor<Face> settings) {
        with("faces", JsonObject::new, faces -> with(faces, side.getName(), JsonObject::new, face -> {
            Face builder = new Face(face);
            settings.process(builder).buildTo(face);
        }));
        return this;
    }

    @Environment(EnvType.CLIENT)
    public static final class Rotation extends TypedJsonBuilder<JsonObject> {
        private Rotation(JsonObject root) { super(root, j->j); }

        public Rotation origin(float x, float y, float z) {
            root.add("origin", arrayOf(
                MathHelper.clamp(x, -16, 32),
                MathHelper.clamp(y, -16, 32),
                MathHelper.clamp(z, -16, 32)
            ));
            return this;
        }

        public Rotation axis(Direction.Axis axis) {
            root.addProperty("axis", axis.getName());
            return this;
        }

        public Rotation angle(float angle) {
            root.addProperty("angle", MathHelper.clamp(angle, -45f, 45f) % 22.5f * 22.5f);
            return this;
        }

        public Rotation rescale(boolean rescale) {
            root.addProperty("rescale", rescale);
            return this;
        }
    }

    @Environment(EnvType.CLIENT)
    public static final class Face extends TypedJsonBuilder<JsonObject> {
        private Face(JsonObject root) { super(root, j->j); }

        public Face uv(float x1, float x2, float y1, float y2) {
            root.add("uv", arrayOf(
                MathHelper.clamp(x1, -16, 32),
                MathHelper.clamp(x2, -16, 32),
                MathHelper.clamp(y1, -16, 32),
                MathHelper.clamp(y2, -16, 32)
            ));
            return this;
        }

        public Face texture(String varName) {
            root.addProperty("texture", "#"+varName);
            return this;
        }

        public Face texture(Identifier path) {
            root.addProperty("texture", path.toString());
            return this;
        }

        public Face cullface(Direction side) {
            root.addProperty("cullface", side.getName());
            return this;
        }

        public Face rotation(int rotation) {
            root.addProperty("rotation", MathHelper.clamp(rotation, 0, 270) % 90 * 90);
            return this;
        }

        public Face tintindex(int tintindex) {
            root.addProperty("tintindex", tintindex);
            return this;
        }
    }
}
