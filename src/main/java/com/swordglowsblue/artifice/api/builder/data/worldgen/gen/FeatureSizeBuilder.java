package com.swordglowsblue.artifice.api.builder.data.worldgen.gen;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;

public class FeatureSizeBuilder extends TypedJsonBuilder<JsonObject> {

    public FeatureSizeBuilder() {
        super(new JsonObject(), j->j);
    }

    public <S extends FeatureSizeBuilder> S type(String type) {
        this.root.addProperty("type", type);
        return (S) this;
    }

    public <S extends FeatureSizeBuilder> S minClippedHeight(int minClippedHeight) {
        if (minClippedHeight > 80) throw new IllegalArgumentException("minClippedHeight can't be higher than 80! Found " + minClippedHeight);
        if (minClippedHeight < 0) throw new IllegalArgumentException("minClippedHeight can't be higher than 0! Found " + minClippedHeight);
        this.root.addProperty("min_clipped_height", minClippedHeight);
        return (S) this;
    }

    public static class TwoLayersFeatureSizeBuilder extends FeatureSizeBuilder {

        public TwoLayersFeatureSizeBuilder() {
            super();
            this.type("minecraft:two_layers_feature_size");
        }

        public TwoLayersFeatureSizeBuilder limit(int limit) {
            if (limit > 81) throw new IllegalArgumentException("limit can't be higher than 81! Found " + limit);
            if (limit < 0) throw new IllegalArgumentException("limit can't be higher than 0! Found " + limit);
            this.root.addProperty("limit", limit);
            return this;
        }

        public TwoLayersFeatureSizeBuilder lowerSize(int lowerSize) {
            if (lowerSize > 16) throw new IllegalArgumentException("lowerSize can't be higher than 16! Found " + lowerSize);
            if (lowerSize < 0) throw new IllegalArgumentException("lowerSize can't be higher than 0! Found " + lowerSize);
            this.root.addProperty("lower_size", lowerSize);
            return this;
        }

        public TwoLayersFeatureSizeBuilder upperSize(int upperSize) {
            if (upperSize > 16) throw new IllegalArgumentException("upperSize can't be higher than 16! Found " + upperSize);
            if (upperSize < 0) throw new IllegalArgumentException("upperSize can't be higher than 0! Found " + upperSize);
            this.root.addProperty("upper_size", upperSize);
            return this;
        }
    }

    public static class ThreeLayersFeatureSizeBuilder extends FeatureSizeBuilder {

        public ThreeLayersFeatureSizeBuilder() {
            super();
            this.type("minecraft:three_layers_feature_size");
        }

        public ThreeLayersFeatureSizeBuilder limit(int limit) {
            if (limit > 80) throw new IllegalArgumentException("limit can't be higher than 80! Found " + limit);
            if (limit < 0) throw new IllegalArgumentException("limit can't be higher than 0! Found " + limit);
            this.root.addProperty("limit", limit);
            return this;
        }

        public ThreeLayersFeatureSizeBuilder upperLimit(int upperLimit) {
            if (upperLimit > 80) throw new IllegalArgumentException("upperLimit can't be higher than 80! Found " + upperLimit);
            if (upperLimit < 0) throw new IllegalArgumentException("upperLimit can't be higher than 0! Found " + upperLimit);
            this.root.addProperty("upper_limit", upperLimit);
            return this;
        }

        public ThreeLayersFeatureSizeBuilder lowerSize(int lowerSize) {
            if (lowerSize > 16) throw new IllegalArgumentException("lowerSize can't be higher than 16! Found " + lowerSize);
            if (lowerSize < 0) throw new IllegalArgumentException("lowerSize can't be higher than 0! Found " + lowerSize);
            this.root.addProperty("lower_size", lowerSize);
            return this;
        }

        public ThreeLayersFeatureSizeBuilder middleSize(int middleSize) {
            if (middleSize > 16) throw new IllegalArgumentException("middleSize can't be higher than 16! Found " + middleSize);
            if (middleSize < 0) throw new IllegalArgumentException("middleSize can't be higher than 0! Found " + middleSize);
            this.root.addProperty("middle_size", middleSize);
            return this;
        }

        public ThreeLayersFeatureSizeBuilder upperSize(int upperSize) {
            if (upperSize > 16) throw new IllegalArgumentException("upperSize can't be higher than 16! Found " + upperSize);
            if (upperSize < 0) throw new IllegalArgumentException("upperSize can't be higher than 0! Found " + upperSize);
            this.root.addProperty("upper_size", upperSize);
            return this;
        }
    }
}
