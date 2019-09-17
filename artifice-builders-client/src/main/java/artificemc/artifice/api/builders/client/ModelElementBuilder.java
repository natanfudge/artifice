package artificemc.artifice.api.builders.client;

import artificemc.artifice.api.builders.TypedJsonBuilder;
import artificemc.artifice.api.core.util.Processor;
import artificemc.artifice.impl.core.util.IdUtils;
import com.google.gson.JsonObject;

/**
 * Builder for an individual model element.
 * @see ModelBuilder
 */
public final class ModelElementBuilder extends TypedJsonBuilder<JsonObject, ModelElementBuilder> {
    ModelElementBuilder() { super(j->j); }

    /**
     * Set the start point of this cuboid.
     * @param x The start point on the X axis. Clamped to between -16 and 32.
     * @param y The start point on the Y axis. Clamped to between -16 and 32.
     * @param z The start point on the Z axis. Clamped to between -16 and 32.
     * @return this
     */
    public ModelElementBuilder from(float x, float y, float z) {
        this.set("from", arrayOf(
            Math.min(Math.max(x, -16), 32),
            Math.min(Math.max(y, -16), 32),
            Math.min(Math.max(z, -16), 32)
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
        this.set("to", arrayOf(
            Math.min(Math.max(x, -16), 32),
            Math.min(Math.max(y, -16), 32),
            Math.min(Math.max(z, -16), 32)
        ));
        return this;
    }

    /**
     * Set the rotation of this cuboid.
     * @param settings A callback which will be passed a {@link Rotation}.
     * @return this
     */
    public ModelElementBuilder rotation(Processor<Rotation> settings) {
        withObject("rotation", rotation -> settings.process(new Rotation()).build());
        return this;
    }

    /**
     * Set whether to render shadows on this cuboid.
     * @param shade Whether to shade (default: true).
     * @return this
     */
    public ModelElementBuilder shade(boolean shade) {
        this.set("shade", shade);
        return this;
    }

    /**
     * Define properties of the face in the given direction.
     * @param side The direction of the face.
     * @param settings A callback which will be passed a {@link Face}.
     * @return this
     */
    public ModelElementBuilder face(Direction side, Processor<Face> settings) {
        withObject("faces", faces -> faces.set(side.getName(), settings.process(new Face()).build()));
        return this;
    }

    /**
     * Builder for model element rotation.
     * @see ModelElementBuilder
     */
    public static final class Rotation extends TypedJsonBuilder<JsonObject, Rotation> {
        private Rotation() { super(j->j); }

        /**
         * Set the origin point of this rotation.
         * @param x The origin point on the X axis. Clamped to between -16 and 32.
         * @param y The origin point on the Y axis. Clamped to between -16 and 32.
         * @param z The origin point on the Z axis. Clamped to between -16 and 32.
         * @return this
         */
        public Rotation origin(float x, float y, float z) {
            this.set("origin", arrayOf(
                Math.min(Math.max(x, -16), 32),
                Math.min(Math.max(y, -16), 32),
                Math.min(Math.max(z, -16), 32)
            ));
            return this;
        }

        /**
         * Set the axis to rotate around.
         * @param axis The axis.
         * @return this
         */
        public Rotation axis(Axis axis) {
            this.set("axis", axis.getName());
            return this;
        }

        /**
         * Set the rotation angle in 22.5deg increments.
         * @param angle The angle.
         * @return this
         * @throws IllegalArgumentException if the angle is not between -45 and 45 or is not divisible by 22.5.
         * TODO: convert instead of throwing
         */
        public Rotation angle(float angle) {
            if(angle != Math.min(Math.max(angle, -45f), 45f) || angle % 22.5f != 0)
                throw new IllegalArgumentException("Angle must be between -45 and 45 in increments of 22.5");
            this.set("angle", angle);
            return this;
        }

        /**
         * Set whether to rescale this element's faces across the whole block.
         * @param rescale Whether to rescale (default: false).
         * @return this
         */
        public Rotation rescale(boolean rescale) {
            this.set("rescale", rescale);
            return this;
        }
    }

    /**
     * Builder for a model element face.
     * @see ModelElementBuilder
     */
    public static final class Face extends TypedJsonBuilder<JsonObject, Face> {
        private Face() { super(j->j); }

        /**
         * Set the texture UV to apply to this face. Detected by position within the block if not specified.
         * @param x1 The start point on the X axis. Clamped to between 0 and 16.
         * @param x2 The end point on the X axis. Clamped to between 0 and 16.
         * @param y1 The start point on the Y axis. Clamped to between 0 and 16.
         * @param y2 The end point on the Y axis. Clamped to between 0 and 16.
         * @return this
         */
        public Face uv(int x1, int x2, int y1, int y2) {
            this.set("uv", arrayOf(
                Math.min(Math.max(x1, 0), 16),
                Math.min(Math.max(x2, 0), 16),
                Math.min(Math.max(y1, 0), 16),
                Math.min(Math.max(y2, 0), 16)
            ));
            return this;
        }

        /**
         * Set the texture of this face to the given texture variable.
         * @param varName The variable name (e.g. {@code particle}).
         * @return this
         */
        public Face texture(String varName) {
            this.set("texture", "#"+varName);
            return this;
        }

        /**
         * Set the texture of this face to the given texture id.
         * @param path The texture path ({@code namespace:type/textureid}).
         * @return this
         */
        public Face texturePath(String path) {
            IdUtils.validateIdentifier(path);
            this.set("texture", path);
            return this;
        }

        /**
         * Set the side of the block for which this face should be culled if touching another block.
         * @param side The side to cull on (defaults to the side specified for this face).
         * @return this
         */
        public Face cullface(Direction side) {
            this.set("cullface", side.getName());
            return this;
        }

        /**
         * Set the rotation of this face's texture in 90deg increments.
         * @param rotation The texture rotation.
         * @return this
         * @throws IllegalArgumentException if the rotation is not between 0 and 270 or is not divisible by 90.
         * TODO: convert instead of throwing
         */
        public Face rotation(int rotation) {
            if(rotation != Math.min(Math.max(rotation, 0), 270) || rotation % 90 != 0)
                throw new IllegalArgumentException("Rotation must be between 0 and 270 in increments of 90");
            this.set("rotation", rotation);
            return this;
        }

        /**
         * Set the tint index of this face. Used by color providers and only applicable for blocks with defined color providers (e.g. grass).
         * @param tintindex The tint index.
         * @return this
         */
        public Face tintindex(int tintindex) {
            this.set("tintindex", tintindex);
            return this;
        }
    }

    public enum Direction {
        UP, DOWN, NORTH, SOUTH, EAST, WEST;
        public String getName() { return this.toString().toLowerCase(); }
    }

    public enum Axis {
        X(Direction.EAST, Direction.WEST),
        Y(Direction.UP, Direction.DOWN),
        Z(Direction.SOUTH, Direction.NORTH);

        public final Direction positive;
        public final Direction negative;

        Axis(Direction positive, Direction negative) {
            this.positive = positive;
            this.negative = negative;
        }

        public String getName() { return this.toString().toLowerCase(); }
    }
}
