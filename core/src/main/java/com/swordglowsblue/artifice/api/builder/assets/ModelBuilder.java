package com.swordglowsblue.artifice.api.builder.assets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

/**
 * Builder for a model file ({@code namespace:models/block|item/modelid.json}).
 * @see <a href="https://minecraft.gamepedia.com/Model" target="_blank">Minecraft Wiki</a>
 */
@Environment(EnvType.CLIENT)
public final class ModelBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public ModelBuilder() { super(new JsonObject(), JsonResource::new); }

    /**
     * Set the parent model for this model to inherit from.
     * @param id The parent model ID ({@code namespace:block|item/modelid}
     * @return this
     */
    public ModelBuilder parent(Identifier id) {
        root.addProperty("parent", id.toString());
        return this;
    }

    /**
     * Associate a texture with the given variable name.
     * @param name The texture variable name.
     * @param path The texture ID ({@code namespace:type/textureid}).
     * @return this
     */
    public ModelBuilder texture(String name, Identifier path) {
        with("textures", JsonObject::new, textures -> textures.addProperty(name, path.toString()));
        return this;
    }

    /**
     * Modify the display transformation properties of this model for the given display position.
     * @param name The position name (e.g. {@code thirdperson_righthand}).
     * @param settings A callback which will be passed a {@link Display}.
     * @return this
     */
    public ModelBuilder display(String name, Processor<Display> settings) {
        with("display", JsonObject::new, display ->
            display.add(name, settings.process(new Display()).build()));
        return this;
    }

    /**
     * Add an element to this model.
     * @param settings A callback which will be passed a {@link ModelElementBuilder}.
     * @return this
     */
    public ModelBuilder element(Processor<ModelElementBuilder> settings) {
        with("elements", JsonArray::new, elements -> elements.add(settings.process(new ModelElementBuilder()).build()));
        return this;
    }

    /**
     * Set whether this model should use ambient occlusion for lighting. Only applicable for block models.
     * @param ambientocclusion Whether to use ambient occlusion.
     * @return this
     */
    public ModelBuilder ambientocclusion(boolean ambientocclusion) {
        this.root.addProperty("ambientocclusion", ambientocclusion);
        return this;
    }

    /**
     * Add a property override to this model. Only applicable for item models.
     * @param settings A callback which will be passed a {@link Override}.
     * @return this
     */
    public ModelBuilder override(Processor<Override> settings) {
        with("overrides", JsonArray::new, overrides -> overrides.add(settings.process(new Override()).build()));
        return this;
    }

    /**
     * Builder for model display settings.
     * @see ModelBuilder
     */
    @Environment(EnvType.CLIENT)
    public static final class Display extends TypedJsonBuilder<JsonObject> {
        private Display() { super(new JsonObject(), j->j); }

        /**
         * Set the rotation of this model around each axis.
         * @param x The rotation around the X axis.
         * @param y The rotation around the Y axis.
         * @param z The rotation around the Z axis.
         * @return this
         */
        public Display rotation(float x, float y, float z) {
            root.add("rotation", arrayOf(x, y, z));
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
            root.add("translation", arrayOf(x, y, z));
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
            root.add("scale", arrayOf(x, y, z));
            return this;
        }
    }

    /**
     * Builder for an item model property override.
     * @see ModelBuilder
     */
    @Environment(EnvType.CLIENT)
    public static final class Override extends TypedJsonBuilder<JsonObject> {
        private Override() { super(new JsonObject(), j->j); }

        /**
         * Set the required value of the given property.
         * Calling this multiple times will require all properties to match.
         * @param name The item property tag.
         * @param value The required integer value.
         * @return this
         * @see <a href="https://minecraft.gamepedia.com/Model#Item_tags">Minecraft Wiki</a>
         */
        public Override predicate(String name, int value) {
            with("predicate", JsonObject::new, predicate -> predicate.addProperty(name, value));
            return this;
        }

        /**
         * Set the model to be used instead of this one if the predicate matches.
         * @param id The model id ({@code namespace:block|item/modelid}).
         * @return this
         */
        public Override model(Identifier id) {
            root.addProperty("model", id.toString());
            return this;
        }
    }
}
