package artificemc.artifice.api.builders.client;

import artificemc.artifice.api.builders.JsonObjectBuilder;
import artificemc.artifice.api.builders.TypedJsonBuilder;
import artificemc.artifice.api.core.resource.JsonResource;
import artificemc.artifice.api.core.util.Processor;
import com.google.gson.JsonArray;

/**
 * Builder for a texture animation file ({@code namespace:textures/block|item/textureid.mcmeta}).
 * @see <a href="https://minecraft.gamepedia.com/Resource_pack#Animation" target="_blank">Minecraft Wiki</a>
 */
public final class AnimationBuilder extends TypedJsonBuilder<JsonResource, AnimationBuilder> {
    public AnimationBuilder() { super(anim ->
        new JsonResource(new JsonObjectBuilder().set("animation", anim).build())); }

    /**
     * Set whether this animation should interpolate between frames with a frametime &gt; 1 between them.
     * @param interpolate Whether to interpolate (default: false).
     * @return this
     */
    public AnimationBuilder interpolate(boolean interpolate) {
        this.set("interpolate", interpolate);
        return this;
    }

    /**
     * Set the frame width of this animation as a ratio of its frame height.
     * @param width The width (default: unset).
     * @return this
     */
    public AnimationBuilder width(int width) {
        this.set("width", width);
        return this;
    }

    /**
     * Set the frame height of this animation as a ratio of its total pixel height.
     * @param height The height (default: unset).
     * @return this
     */
    public AnimationBuilder height(int height) {
        this.set("height", height);
        return this;
    }

    /**
     * Set the default time to spend on each frame.
     * @param frametime The number of ticks the frame should last (default: 1)
     * @return this
     */
    public AnimationBuilder frametime(int frametime) {
        this.set("frametime", frametime);
        return this;
    }

    /**
     * Set the frame order and/or frame-specific timings of this animation.
     * @param settings A callback which will be passed a {@link FrameOrder}.
     * @return this
     */
    public AnimationBuilder frames(Processor<FrameOrder> settings) {
        this.set("frames", settings.process(new FrameOrder()).build());
        return this;
    }

    /**
     * Builder for the `frames` property of a texture animation file.
     * @see AnimationBuilder
     */
    public static final class FrameOrder {
        private final JsonArray frames = new JsonArray();
        private FrameOrder() {}
        JsonArray build() { return frames; }

        /**
         * Add a single frame to end of the order.
         * @param index The frame index (starting from 0 at the top of the texture).
         * @return this
         */
        public FrameOrder frame(int index) {
            frames.add(index);
            return this;
        }

        /**
         * Add a single frame to the end of the order, with a modified frametime specified.
         * @param index The frame index (starting from 0 at the top of the texture).
         * @param frametime The number of ticks the frame should last.
         * @return this
         */
        public FrameOrder frame(int index, int frametime) {
            frames.add(new JsonObjectBuilder().set("index", index).set("time", frametime).build());
            return this;
        }

        /**
         * Add a range of frame indexes to this animation.
         * @param start The first frame index to add (inclusive).
         * @param endExclusive The last frame index to add (exclusive).
         * @return this
         * @see FrameOrder#frame
         */
        public FrameOrder frameRange(int start, int endExclusive) {
            for(int i = start; i < endExclusive; i++) frames.add(i);
            return this;
        }
    }
}
