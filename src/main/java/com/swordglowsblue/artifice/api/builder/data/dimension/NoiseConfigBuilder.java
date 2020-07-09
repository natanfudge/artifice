package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class NoiseConfigBuilder extends TypedJsonBuilder<JsonObject> {
    protected NoiseConfigBuilder() {
        super(new JsonObject(), j->j);
    }

    /**
     * @param height
     * @return
     */
    public NoiseConfigBuilder height(int height) {
        this.root.addProperty("height", height);
        return this;
    }

    /**
     * @param sizeHorizontal
     * @return
     */
    public NoiseConfigBuilder sizeHorizontal(int sizeHorizontal) {
        this.root.addProperty("size_horizontal", sizeHorizontal);
        return this;
    }

    /**
     * @param sizeVertical
     * @return
     */
    public NoiseConfigBuilder sizeVertical(int sizeVertical) {
        this.root.addProperty("size_vertical", sizeVertical);
        return this;
    }

    /**
     * @param densityFactor
     * @return
     */
    public NoiseConfigBuilder densityFactor(double densityFactor) {
        this.root.addProperty("density_factor", densityFactor);
        return this;
    }

    @Deprecated
    public NoiseConfigBuilder densityOffset(int densityOffset) {
        return this.densityOffset((double) densityOffset);
    }

    /**
     * @param densityOffset
     * @return
     */
    public NoiseConfigBuilder densityOffset(double densityOffset) {
        if (densityOffset > 1) {
            densityOffset = 1;
        }
        if (densityOffset < -1) {
            densityOffset = -1;
        }
        this.root.addProperty("density_offset", densityOffset);
        return this;
    }

    /**
     * @param simplexSurfaceNoise
     * @return
     */
    public NoiseConfigBuilder simplexSurfaceNoise(boolean simplexSurfaceNoise) {
        this.root.addProperty("simplex_surface_noise", simplexSurfaceNoise);
        return this;
    }

    /**
     * @param randomDensityOffset
     * @return
     */
    public NoiseConfigBuilder randomDensityOffset(boolean randomDensityOffset) {
        this.root.addProperty("random_density_offset", randomDensityOffset);
        return this;
    }

    /**
     * @param islandNoiseOverride
     * @return
     */
    public NoiseConfigBuilder islandNoiseOverride(boolean islandNoiseOverride) {
        this.root.addProperty("island_noise_override", islandNoiseOverride);
        return this;
    }

    /**
     * @param amplified
     * @return
     */
    public NoiseConfigBuilder amplified(boolean amplified) {
        this.root.addProperty("amplified", amplified);
        return this;
    }

    /**
     * Build noise sampling config.
     * @param noiseSamplingConfigBuilder
     * @return
     */
    public NoiseConfigBuilder sampling(Processor<NoiseSamplingConfigBuilder> noiseSamplingConfigBuilder) {
        with("sampling", JsonObject::new, jsonObject -> noiseSamplingConfigBuilder.process(new NoiseSamplingConfigBuilder()).buildTo(jsonObject));
        return this;
    }

    /**
     * Build slide config.
     * @param id
     * @param slideConfigBuilderProcessor
     * @return
     */
    private NoiseConfigBuilder slideConfig(String id, Processor<SlideConfigBuilder> slideConfigBuilderProcessor) {
        with(id, JsonObject::new, jsonObject -> slideConfigBuilderProcessor.process(new SlideConfigBuilder()).buildTo(jsonObject));
        return this;
    }

    /**
     * Build top slide.
     * @param slideConfigBuilder
     * @return
     */
    public NoiseConfigBuilder topSlide(Processor<SlideConfigBuilder> slideConfigBuilder) {
        return this.slideConfig("top_slide", slideConfigBuilder);
    }

    /**
     * Build bottom slide.
     * @param slideConfigBuilder
     * @return
     */
    public NoiseConfigBuilder bottomSlide(Processor<SlideConfigBuilder> slideConfigBuilder) {
        return this.slideConfig("bottom_slide", slideConfigBuilder);
    }

    public static class NoiseSamplingConfigBuilder extends TypedJsonBuilder<JsonObject> {

        protected NoiseSamplingConfigBuilder() {
            super(new JsonObject(), j->j);
        }

        /**
         * @param xzScale
         * @return
         */
        public NoiseSamplingConfigBuilder xzScale(double xzScale) {
            this.root.addProperty("xz_scale", xzScale);
            return this;
        }

        /**
         * @param yScale
         * @return
         */
        public NoiseSamplingConfigBuilder yScale(double yScale) {
            this.root.addProperty("y_scale", yScale);
            return this;
        }

        /**
         * @param xzFactor
         * @return
         */
        public NoiseSamplingConfigBuilder xzFactor(double xzFactor) {
            this.root.addProperty("xz_factor", xzFactor);
            return this;
        }

        /**
         * @param yFactor
         * @return
         */
        public NoiseSamplingConfigBuilder yFactor(double yFactor) {
            this.root.addProperty("y_factor", yFactor);
            return this;
        }
    }

    public static class SlideConfigBuilder extends TypedJsonBuilder<JsonObject> {

        protected SlideConfigBuilder() {
            super(new JsonObject(), j->j);
        }

        /**
         * @param offset
         * @return
         */
        public SlideConfigBuilder offset(int offset) {
            this.root.addProperty("offset", offset);
            return this;
        }

        /**
         * @param size
         * @return
         */
        public SlideConfigBuilder size(int size) {
            this.root.addProperty("size", size);
            return this;
        }

        /**
         * @param target
         * @return
         */
        public SlideConfigBuilder target(int target) {
            this.root.addProperty("target", target);
            return this;
        }
    }
}
