package artificemc.artifice.api.builders.client;

import artificemc.artifice.api.builders.JsonArrayBuilder;
import artificemc.artifice.api.builders.JsonObjectBuilder;
import artificemc.artifice.api.builders.TypedJsonBuilder;
import artificemc.artifice.api.core.resource.JsonResource;
import artificemc.artifice.api.core.util.Processor;
import artificemc.artifice.impl.core.util.IdUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Builder for a blockstate definition file ({@code namespace:blockstates/blockid.json}).
 * @see <a href="https://minecraft.gamepedia.com/Model#Block_states" target="_blank">Minecraft Wiki</a>
 */
public final class BlockStateBuilder extends TypedJsonBuilder<JsonResource, BlockStateBuilder> {
    public BlockStateBuilder() { super(JsonResource::new); }

    /**
     * Add a variant for the given state key.
     * Calling this multiple times for the same key will overwrite the existing value.
     * {@code variant} and {@code multipart} are incompatible; calling this will remove any existing {@code multipart} definitions.
     *
     * @param name The state key ({@code ""} for default or format: {@code "prop1=value,prop2=value"}).
     * @param settings A callback which will be passed a {@link Variant}.
     * @return this
     */
    public BlockStateBuilder variant(String name, Processor<Variant> settings) {
        root.remove("multipart");
        withObject("variants", variants -> variants.set(name, settings.process(new Variant()).build()));
        return this;
    }

    /**
     * Add a variant for the given state key, with multiple weighted random options.
     * Calling this multiple times for the same key will add to the list instead of overwriting.
     * {@code variant} and {@code multipart} are incompatible; calling this will remove any existing {@code multipart} definitions.
     *
     * @param name The state key ({@code ""} for default or format: {@code "prop1=value,prop2=value"}).
     * @param settings A callback which will be passed a {@link Variant}.
     * @return this
     */
    public BlockStateBuilder weightedVariant(String name, Processor<Variant> settings) {
        root.remove("multipart");
        withObject("variants", variants -> withArray(variants.build(), name, options ->
           options.add(settings.process(new Variant()).build())));
        return this;
    }

    /**
     * Add a multipart case.
     * Calling this multiple times will add to the list instead of overwriting.
     * {@code variant} and {@code multipart} are incompatible; calling this will remove any existing {@code variant} definitions.
     *
     * @param settings A callback which will be passed a {@link Case}.
     * @return this
     */
    public BlockStateBuilder multipartCase(Processor<Case> settings) {
        root.remove("variants");
        withArray("multipart", cases -> cases.add(settings.process(new Case()).build()));
        return this;
    }

    /**
     * Builder for a blockstate variant definition.
     * @see BlockStateBuilder
     */
    public static final class Variant extends TypedJsonBuilder<JsonObject, Variant> {
        private Variant() { super(j->j); }

        /**
         * Set the model this variant should use.
         * @param id The model ID ({@code namespace:block|item/modelid}).
         * @return this
         */
        public Variant model(String id) {
            IdUtils.validateIdentifier(id);
            this.set("model", id);
            return this;
        }

        /**
         * Set the rotation of this variant around the X axis in increments of 90deg.
         * @param x The X axis rotation.
         * @return this
         * @throws IllegalArgumentException if {@code x} is not divisible by 90.
         * TODO: convert instead of throwing
         */
        public Variant rotationX(int x) {
            if(x % 90 != 0) throw new IllegalArgumentException("X rotation must be in increments of 90");
            this.set("x", x);
            return this;
        }

        /**
         * Set the rotation of this variant around the Y axis in increments of 90deg.
         * @param y The Y axis rotation.
         * @return this
         * @throws IllegalArgumentException if {@code y} is not divisible by 90.
         * TODO: convert instead of throwing
         */
        public Variant rotationY(int y) {
            if(y % 90 != 0) throw new IllegalArgumentException("Y rotation must be in increments of 90");
            this.set("y", y);
            return this;
        }

        /**
         * Set whether the textures of this model should not rotate with it.
         * @param uvlock Whether to lock texture rotation or not.
         * @return this
         */
        public Variant uvlock(boolean uvlock) {
            this.set("uvlock", uvlock);
            return this;
        }

        /**
         * Set the relative weight of this variant.
         * This property will be ignored if the variant is not added with {@link BlockStateBuilder#weightedVariant}
         *  or {@link Case#weightedApply}.
         * @param weight The weight.
         * @return this
         */
        public Variant weight(int weight) {
            this.set("weight", weight);
            return this;
        }
    }

    /**
     * Builder for a blockstate multipart case.
     * @see BlockStateBuilder
     */
    public static final class Case extends TypedJsonBuilder<JsonObject, Case> {
        private Case() { super(new JsonObject(), j->j); }

        /**
         * Set the condition for this case to be applied.
         * Calling this multiple times with different keys will require all of the specified properties to match.
         * @param name The state name (e.g. {@code facing}).
         * @param state The state value (e.g. {@code north}).
         * @return this
         */
        public Case when(String name, String state) {
            withObject("when", when -> {
                when.set("OR", (JsonElement)null);
                when.set(name, state);
            });
            return this;
        }

        /**
         * Set the condition for this case to be applied.
         * Calling this multiple times with different keys will require at least one of the specified properties to match.
         * @param name The state name (e.g. {@code facing}).
         * @param state The state value (e.g. {@code north}).
         * @return this
         */
        public Case whenAny(String name, String state) {
            this.set("when", new JsonObjectBuilder()
                .set("OR", new JsonArrayBuilder()
                    .add(new JsonObjectBuilder().set(name, state).build())
                    .build())
                .build());
            return this;
        }

        /**
         * Set the variant to be applied if the condition matches.
         * Calling this multiple times for the same key will overwrite the existing value.
         * @param settings A callback which will be passed a {@link Variant}.
         * @return this
         */
        public Case apply(Processor<Variant> settings) {
            this.set("apply", settings.process(new Variant()).build());
            return this;
        }

        /**
         * Set the variant to be applied if the condition matches, with multiple weighted random options.
         * Calling this multiple times will add to the list instead of overwriting.
         * @param settings A callback which will be passed a {@link Variant}.
         * @return this
         */
        public Case weightedApply(Processor<Variant> settings) {
            withArray("apply", options -> options.add(settings.process(new Variant()).build()));
            return this;
        }
    }
}
