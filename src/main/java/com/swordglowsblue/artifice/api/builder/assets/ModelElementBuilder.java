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
        with("rotation", JsonObject::new, rotation -> settings.process(new Rotation(rotation)).buildTo(rotation));
        return this;
    }

    public ModelElementBuilder shade(boolean shade) {
        root.addProperty("shade", shade);
        return this;
    }

    public ModelElementBuilder face(Direction side, Processor<Face> settings) {
        with("faces", JsonObject::new, faces -> with(faces, side.getName(), JsonObject::new, face ->
            settings.process(new Face(face)).buildTo(face)));
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
            if(angle != MathHelper.clamp(angle, -45f, 45f) || angle % 22.5f != 0)
                throw new IllegalArgumentException("Angle must be between -45 and 45 in increments of 22.5");
            root.addProperty("angle", angle);
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

        public Face uv(int x1, int x2, int y1, int y2) {
            root.add("uv", arrayOf(
                MathHelper.clamp(x1, 0, 16),
                MathHelper.clamp(x2, 0, 16),
                MathHelper.clamp(y1, 0, 16),
                MathHelper.clamp(y2, 0, 16)
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
            if(rotation != MathHelper.clamp(rotation, 0, 270) || rotation % 90 != 0)
                throw new IllegalArgumentException("Rotation must be between 0 and 270 in increments of 90");
            root.addProperty("rotation", rotation);
            return this;
        }

        public Face tintindex(int tintindex) {
            root.addProperty("tintindex", tintindex);
            return this;
        }
    }
}
