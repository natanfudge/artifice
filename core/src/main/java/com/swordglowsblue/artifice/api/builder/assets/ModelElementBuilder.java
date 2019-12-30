package com.swordglowsblue.artifice.api.builder.assets;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

/**
 * Builder for an individual model element.
 * @see ModelBuilder
 */
@Environment(EnvType.CLIENT)
public final class ModelElementBuilder extends TypedJsonBuilder<JsonObject> {
    ModelElementBuilder() { super(new JsonObject(), j->j); }

    /**
     * Set the start point of this cuboid.
     * @param x The start point on the X axis. Clamped to between -16 and 32.
     * @param y The start point on the Y axis. Clamped to between -16 and 32.
     * @param z The start point on the Z axis. Clamped to between -16 and 32.
     * @return this
     */
    public ModelElementBuilder from(float x, float y, float z) {
        root.add("from", arrayOf(
            MathHelper.clamp(x, -16, 32),
            MathHelper.clamp(y, -16, 32),
            MathHelper.clamp(z, -16, 32)
        ));
        return this;
    }

    /**
     * Set the end point of this cuboid.
     * @param x The end point on the X axis. Clamped to between -16 and 32.
     * @param y The end point on the Y axis. Clamped to between -16 and 32.
     * @param z The end point on the Z axis. Clamped to between -16 and 32.
     * @return this
     */
    public ModelElementBuilder to(float x, float y, float z) {
        root.add("to", arrayOf(
            MathHelper.clamp(x, -16, 32),
            MathHelper.clamp(y, -16, 32),
            MathHelper.clamp(z, -16, 32)
        ));
        return this;
    }

    /**
     * Set the rotation of this cuboid.
     * @param settings A callback which will be passed a {@link Rotation}.
     * @return this
     */
    public ModelElementBuilder rotation(Processor<Rotation> settings) {
        with("rotation", JsonObject::new, rotation -> settings.process(new Rotation(rotation)).buildTo(rotation));
        return this;
    }

    /**
     * Set whether to render shadows on this cuboid.
     * @param shade Whether to shade (default: true).
     * @return this
     */
    public ModelElementBuilder shade(boolean shade) {
        root.addProperty("shade", shade);
        return this;
    }

    /**
     * Define properties of the face in the given direction.
     * @param side The direction of the face.
     * @param settings A callback which will be passed a {@link Face}.
     * @return this
     */
    public ModelElementBuilder face(Direction side, Processor<Face> settings) {
        with("faces", JsonObject::new, faces -> with(faces, side.getName(), JsonObject::new, face ->
            settings.process(new Face(face)).buildTo(face)));
        return this;
    }

    /**
     * Builder for model element rotation.
     * @see ModelElementBuilder
     */
    @Environment(EnvType.CLIENT)
    public static final class Rotation extends TypedJsonBuilder<JsonObject> {
        private Rotation(JsonObject root) { super(root, j->j); }

        /**
         * Set the origin point of this rotation.
         * @param x The origin point on the X axis. Clamped to between -16 and 32.
         * @param y The origin point on the Y axis. Clamped to between -16 and 32.
         * @param z The origin point on the Z axis. Clamped to between -16 and 32.
         * @return this
         */
        public Rotation origin(float x, float y, float z) {
            root.add("origin", arrayOf(
                MathHelper.clamp(x, -16, 32),
                MathHelper.clamp(y, -16, 32),
                MathHelper.clamp(z, -16, 32)
            ));
            return this;
        }

        /**
         * Set the axis to rotate around.
         * @param axis The axis.
         * @return this
         */
        public Rotation axis(Direction.Axis axis) {
            root.addProperty("axis", axis.getName());
            return this;
        }

        /**
         * Set the rotation angle in 22.5deg increments.
         * @param angle The angle.
         * @return this
         * @throws IllegalArgumentException if the angle is not between -45 and 45 or is not divisible by 22.5.
         */
        public Rotation angle(float angle) {
            if(angle != MathHelper.clamp(angle, -45f, 45f) || angle % 22.5f != 0)
                throw new IllegalArgumentException("Angle must be between -45 and 45 in increments of 22.5");
            root.addProperty("angle", angle);
            return this;
        }

        /**
         * Set whether to rescale this element's faces across the whole block.
         * @param rescale Whether to rescale (default: false).
         * @return this
         */
        public Rotation rescale(boolean rescale) {
            root.addProperty("rescale", rescale);
            return this;
        }
    }

    /**
     * Builder for a model element face.
     * @see ModelElementBuilder
     */
    @Environment(EnvType.CLIENT)
    public static final class Face extends TypedJsonBuilder<JsonObject> {
        private Face(JsonObject root) { super(root, j->j); }

        /**
         * Set the texture UV to apply to this face. Detected by position within the block if not specified.
         * @param x1 The start point on the X axis. Clamped to between 0 and 16.
         * @param x2 The end point on the X axis. Clamped to between 0 and 16.
         * @param y1 The start point on the Y axis. Clamped to between 0 and 16.
         * @param y2 The end point on the Y axis. Clamped to between 0 and 16.
         * @return this
         */
        public Face uv(int x1, int x2, int y1, int y2) {
            root.add("uv", arrayOf(
                MathHelper.clamp(x1, 0, 16),
                MathHelper.clamp(x2, 0, 16),
                MathHelper.clamp(y1, 0, 16),
                MathHelper.clamp(y2, 0, 16)
            ));
            return this;
        }

        /**
         * Set the texture of this face to the given texture variable.
         * @param varName The variable name (e.g. {@code particle}).
         * @return this
         */
        public Face texture(String varName) {
            root.addProperty("texture", "#"+varName);
            return this;
        }

        /**
         * Set the texture of this face to the given texture id.
         * @param path The texture path ({@code namespace:type/textureid}).
         * @return this
         */
        public Face texture(Identifier path) {
            root.addProperty("texture", path.toString());
            return this;
        }

        /**
         * Set the side of the block for which this face should be culled if touching another block.
         * @param side The side to cull on (defaults to the side specified for this face).
         * @return this
         */
        public Face cullface(Direction side) {
            root.addProperty("cullface", side.getName());
            return this;
        }

        /**
         * Set the rotation of this face's texture in 90deg increments.
         * @param rotation The texture rotation.
         * @return this
         * @throws IllegalArgumentException if the rotation is not between 0 and 270 or is not divisible by 90.
         */
        public Face rotation(int rotation) {
            if(rotation != MathHelper.clamp(rotation, 0, 270) || rotation % 90 != 0)
                throw new IllegalArgumentException("Rotation must be between 0 and 270 in increments of 90");
            root.addProperty("rotation", rotation);
            return this;
        }

        /**
         * Set the tint index of this face. Used by color providers and only applicable for blocks with defined color providers (e.g. grass).
         * @param tintindex The tint index.
         * @return this
         */
        public Face tintindex(int tintindex) {
            root.addProperty("tintindex", tintindex);
            return this;
        }
    }
}
