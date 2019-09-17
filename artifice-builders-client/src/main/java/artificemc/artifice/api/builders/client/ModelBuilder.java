package artificemc.artifice.api.builders.client;

import artificemc.artifice.api.builders.TypedJsonBuilder;
import artificemc.artifice.api.core.resource.JsonResource;
import artificemc.artifice.api.core.util.Processor;
import artificemc.artifice.impl.core.util.IdUtils;
import com.google.gson.JsonObject;

/**
 * Builder for a model file ({@code namespace:models/block|item/modelid.json}).
 * @see <a href="https://minecraft.gamepedia.com/Model" target="_blank">Minecraft Wiki</a>
 */
public final class ModelBuilder extends TypedJsonBuilder<JsonResource, ModelBuilder> {
    public ModelBuilder() { super(JsonResource::new); }

    /**
     * Set the parent model for this model to inherit from.
     * @param id The parent model ID ({@code namespace:block|item/modelid}
     * @return this
     */
    public ModelBuilder parent(String id) {
        IdUtils.validateIdentifier(id);
        this.set("parent", id);
        return this;
    }

    /**
     * Associate a texture with the given variable name.
     * @param name The texture variable name.
     * @param path The texture ID ({@code namespace:type/textureid}).
     * @return this
     */
    public ModelBuilder texture(String name, String path) {
        IdUtils.validateIdentifier(path);
        withObject("textures", textures -> textures.set(name, path));
        return this;
    }

    /**
     * Modify the display transformation properties of this model for the given display position.
     * @param name The position name (e.g. {@code thirdperson_righthand}).
     * @param settings A callback which will be passed a {@link Display}.
     * @return this
     */
    public ModelBuilder display(String name, Processor<Display> settings) {
        withObject("display", display ->
            display.set(name, settings.process(new Display()).build()));
        return this;
    }

    /**
     * Add an element to this model.
     * @param settings A callback which will be passed a {@link ModelElementBuilder}.
     * @return this
     */
    public ModelBuilder element(Processor<ModelElementBuilder> settings) {
        withArray("elements", elements -> elements.add(settings.process(new ModelElementBuilder()).build()));
        return this;
    }

    /**
     * Set whether this model should use ambient occlusion for lighting. Only applicable for block models.
     * @param ambientocclusion Whether to use ambient occlusion.
     * @return this
     */
    public ModelBuilder ambientocclusion(boolean ambientocclusion) {
        this.set("ambientocclusion", ambientocclusion);
        return this;
    }

    /**
     * Add a property override to this model. Only applicable for item models.
     * @param settings A callback which will be passed a {@link Override}.
     * @return this
     */
    public ModelBuilder override(Processor<Override> settings) {
        withArray("overrides", overrides -> overrides.add(settings.process(new Override()).build()));
        return this;
    }

    /**
     * Builder for model display settings.
     * @see ModelBuilder
     */
    public static final class Display extends TypedJsonBuilder<JsonObject, Display> {
        private Display() { super(j->j); }

        /**
         * Set the rotation of this model around each axis.
         * @param x The rotation around the X axis.
         * @param y The rotation around the Y axis.
         * @param z The rotation around the Z axis.
         * @return this
         */
        public Display rotation(float x, float y, float z) {
            this.set("rotation", arrayOf(x, y, z));
            return this;
        }

        /**
         * Set the translation of this model along each axis.
         * @param x The translation along the X axis. Clamped to between -80 and 80.
         * @param y The translation along the Y axis. Clamped to between -80 and 80.
         * @param z The translation along the Z axis. Clamped to between -80 and 80.
         * @return this
         */
        public Display translation(float x, float y, float z) {
            this.set("translation", arrayOf(x, y, z));
            return this;
        }

        /**
         * Set the scale of this model on each axis.
         * @param x The scale on the X axis. Clamped to &lt;= 4.
         * @param y The scale on the Y axis. Clamped to &lt;= 4.
         * @param z The scale on the Z axis. Clamped to &lt;= 4.
         * @return this
         */
        public Display scale(float x, float y, float z) {
            this.set("scale", arrayOf(x, y, z));
            return this;
        }
    }

    /**
     * Builder for an item model property override.
     * @see ModelBuilder
     */
    public static final class Override extends TypedJsonBuilder<JsonObject, Override> {
        private Override() { super(j->j); }

        /**
         * Set the required value of the given property.
         * Calling this multiple times will require all properties to match.
         * @param name The item property tag.
         * @param value The required integer value.
         * @return this
         * @see <a href="https://minecraft.gamepedia.com/Model#Item_tags">Minecraft Wiki</a>
         */
        public Override predicate(String name, int value) {
            withObject("predicate", predicate -> predicate.set(name, value));
            return this;
        }

        /**
         * Set the model to be used instead of this one if the predicate matches.
         * @param id The model id ({@code namespace:block|item/modelid}).
         * @return this
         */
        public Override model(String id) {
            IdUtils.validateIdentifier(id);
            this.set("model", id);
            return this;
        }
    }
}
