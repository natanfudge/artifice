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
        if (height > 255) throw new IllegalArgumentException("height can't be higher than 255! Found " + height);
        if (height < 0) throw new IllegalArgumentException("height can't be smaller than 0! Found " + height);
        this.root.addProperty("height", height);
        return this;
    }

    /**
     * @param sizeHorizontal
     * @return
     */
    public NoiseConfigBuilder sizeHorizontal(int sizeHorizontal) {
        if (sizeHorizontal > 4) throw new IllegalArgumentException("sizeHorizontal can't be higher than 4! Found " + sizeHorizontal);
        if (sizeHorizontal < 1) throw new IllegalArgumentException("sizeHorizontal can't be smaller than 1! Found " + sizeHorizontal);
        this.root.addProperty("size_horizontal", sizeHorizontal);
        return this;
    }

    /**
     * @param sizeVertical
     * @return
     */
    public NoiseConfigBuilder sizeVertical(int sizeVertical) {
        if (sizeVertical > 4) throw new IllegalArgumentException("Sealevel can't be higher than 4! Found " + sizeVertical);
        if (sizeVertical < 1) throw new IllegalArgumentException("Sealevel can't be smaller than 1! Found " + sizeVertical);
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
    
    /**
     * @deprecated use the double version instead
     */
    @Deprecated
    public NoiseConfigBuilder densityOffset(int densityOffset) {
        return this.densityOffset((double) densityOffset);
    }

    /**
     * @param densityOffset
     * @return
     */
    public NoiseConfigBuilder densityOffset(double densityOffset) {
        if (densityOffset > 1) throw new IllegalArgumentException("densityOffset can't be higher than 1! Found " + densityOffset);
        if (densityOffset < -1) throw new IllegalArgumentException("densityOffset can't be smaller than -1! Found " + densityOffset);
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
            if (xzScale > 1000.0D) throw new IllegalArgumentException("xzScale can't be higher than 1000.0D! Found " + xzScale);
            if (xzScale < 0.001D) throw new IllegalArgumentException("xzScale can't be smaller than 0.001D! Found " + xzScale);
            this.root.addProperty("xz_scale", xzScale);
            return this;
        }

        /**
         * @param yScale
         * @return
         */
        public NoiseSamplingConfigBuilder yScale(double yScale) {
            if (yScale > 1000.0D) throw new IllegalArgumentException("yScale can't be higher than 1000.0D! Found " + yScale);
            if (yScale < 0.001D) throw new IllegalArgumentException("yScale can't be smaller than 0.001D! Found " + yScale);
            this.root.addProperty("y_scale", yScale);
            return this;
        }

        /**
         * @param xzFactor
         * @return
         */
        public NoiseSamplingConfigBuilder xzFactor(double xzFactor) {
            if (xzFactor > 1000.0D) throw new IllegalArgumentException("xzFactor can't be higher than 1000.0D! Found " + xzFactor);
            if (xzFactor < 0.001D) throw new IllegalArgumentException("xzFactor can't be smaller than 0.001D! Found " + xzFactor);
            this.root.addProperty("xz_factor", xzFactor);
            return this;
        }

        /**
         * @param yFactor
         * @return
         */
        public NoiseSamplingConfigBuilder yFactor(double yFactor) {
            if (yFactor > 1000.0D) throw new IllegalArgumentException("yFactor can't be higher than 1000.0D! Found " + yFactor);
            if (yFactor < 0.001D) throw new IllegalArgumentException("yFactor can't be smaller than 0.001D! Found " + yFactor);
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
            if (size > 255) throw new IllegalArgumentException("size can't be higher than 255! Found " + size);
            if (size < 0) throw new IllegalArgumentException("size can't be smaller than 0! Found " + size);
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
