package com.swordglowsblue.artifice.api.builder.data.dimension;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.util.Processor;

public class NoiseConfigBuilder extends TypedJsonBuilder<JsonObject> {
    protected NoiseConfigBuilder() {
        super(new JsonObject(), j->j);
    }

    public NoiseConfigBuilder height(int height) {
        this.root.addProperty("height", height);
        return this;
    }

    public NoiseConfigBuilder sizeHorizontal(int sizeHorizontal) {
        this.root.addProperty("size_horizontal", sizeHorizontal);
        return this;
    }

    public NoiseConfigBuilder sizeVertical(int sizeVertical) {
        this.root.addProperty("size_vertical", sizeVertical);
        return this;
    }

    public NoiseConfigBuilder densityFactor(double densityFactor) {
        this.root.addProperty("density_factor", densityFactor);
        return this;
    }

    public NoiseConfigBuilder densityOffset(int densityOffset) {
        this.root.addProperty("density_offset", densityOffset);
        return this;
    }

    public NoiseConfigBuilder simplexSurfaceNoise(boolean simplexSurfaceNoise) {
        this.root.addProperty("simplex_surface_noise", simplexSurfaceNoise);
        return this;
    }

    public NoiseConfigBuilder randomDensityOffset(boolean randomDensityOffset) {
        this.root.addProperty("random_density_offset", randomDensityOffset);
        return this;
    }

    public NoiseConfigBuilder islandNoiseOverride(boolean islandNoiseOverride) {
        this.root.addProperty("island_noise_override", islandNoiseOverride);
        return this;
    }

    public NoiseConfigBuilder amplified(boolean amplified) {
        this.root.addProperty("amplified", amplified);
        return this;
    }

    public NoiseConfigBuilder sampling(Processor<NoiseSamplingConfigBuilder> noiseSamplingConfigBuilder) {
        with("sampling", JsonObject::new, jsonObject -> noiseSamplingConfigBuilder.process(new NoiseSamplingConfigBuilder()).buildTo(jsonObject));
        return this;
    }

    private NoiseConfigBuilder slideConfig(String id, Processor<SlideConfigBuilder> slideConfigBuilderProcessor) {
        with(id, JsonObject::new, jsonObject -> slideConfigBuilderProcessor.process(new SlideConfigBuilder()).buildTo(jsonObject));
        return this;
    }

    public NoiseConfigBuilder topSlide(Processor<SlideConfigBuilder> slideConfigBuilder) {
        return this.slideConfig("top_slide", slideConfigBuilder);
    }

    public NoiseConfigBuilder bottomSlide(Processor<SlideConfigBuilder> slideConfigBuilder) {
        return this.slideConfig("bottom_slide", slideConfigBuilder);
    }

    public static class NoiseSamplingConfigBuilder extends TypedJsonBuilder<JsonObject> {

        protected NoiseSamplingConfigBuilder() {
            super(new JsonObject(), j->j);
        }

        public NoiseSamplingConfigBuilder xzScale(double xzScale) {
            this.root.addProperty("xz_scale", xzScale);
            return this;
        }

        public NoiseSamplingConfigBuilder yScale(double yScale) {
            this.root.addProperty("y_scale", yScale);
            return this;
        }

        public NoiseSamplingConfigBuilder xzFactor(double xzFactor) {
            this.root.addProperty("xz_factor", xzFactor);
            return this;
        }

        public NoiseSamplingConfigBuilder yFactor(double yFactor) {
            this.root.addProperty("y_factor", yFactor);
            return this;
        }
    }

    public static class SlideConfigBuilder extends TypedJsonBuilder<JsonObject> {

        protected SlideConfigBuilder() {
            super(new JsonObject(), j->j);
        }

        public SlideConfigBuilder offset(int offset) {
            this.root.addProperty("offset", offset);
            return this;
        }

        public SlideConfigBuilder size(int size) {
            this.root.addProperty("size", size);
            return this;
        }

        public SlideConfigBuilder target(int target) {
            this.root.addProperty("target", target);
            return this;
        }
    }
}
