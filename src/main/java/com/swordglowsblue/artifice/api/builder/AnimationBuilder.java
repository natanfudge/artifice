package com.swordglowsblue.artifice.api.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.impl.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class AnimationBuilder extends JsonBuilder<JsonResource> {
    public AnimationBuilder() { super(new JsonObject(), anim -> {
        JsonObject json = new JsonObject();
        json.add("animation", anim);
        return new JsonResource(json);
    }); }

    public AnimationBuilder interpolate(boolean interpolate) {
        root.addProperty("interpolate", interpolate);
        return this;
    }

    public AnimationBuilder width(int width) {
        root.addProperty("width", width);
        return this;
    }

    public AnimationBuilder height(int height) {
        root.addProperty("height", height);
        return this;
    }

    public AnimationBuilder frametime(int frametime) {
        root.addProperty("frametime", frametime);
        return this;
    }

    public AnimationBuilder frames(Processor<FrameOrder> settings) {
        root.add("frames", settings.process(new FrameOrder()).build());
        return this;
    }

    @Environment(EnvType.CLIENT)
    public static final class FrameOrder {
        private final JsonArray frames = new JsonArray();
        private FrameOrder() {}

        public FrameOrder frame(int index) {
            frames.add(index);
            return this;
        }

        public FrameOrder frame(int index, int frametime) {
            JsonObject frame = new JsonObject();
            frame.addProperty("index", index);
            frame.addProperty("time", frametime);
            frames.add(frame);
            return this;
        }

        public FrameOrder frameRange(int start, int endExclusive) {
            for(int i = start; i < endExclusive; i++) frames.add(i);
            return this;
        }

        JsonArray build() { return frames; }
    }
}
