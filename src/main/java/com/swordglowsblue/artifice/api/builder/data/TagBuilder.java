package com.swordglowsblue.artifice.api.builder.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import net.minecraft.util.Identifier;

/**
 * Builder for tag files ({@code namespace:tags/type/tagid.json}).
 * @see <a href="https://minecraft.gamepedia.com/Tag" target="_blank">Minecraft Wiki</a>
 */
public final class TagBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public TagBuilder() { super(new JsonObject(), JsonResource::new); }

    /**
     * Set whether this tag should override or append to versions of the same tag in lower priority data packs.
     * @param replace Whether to replace.
     * @return this
     */
    public TagBuilder replace(boolean replace) {
        root.addProperty("replace", replace);
        return this;
    }

    /**
     * Add a value to this tag.
     * @param id The value ID.
     * @return this
     */
    public TagBuilder value(Identifier id) {
        with("values", JsonArray::new, values -> values.add(id.toString()));
        return this;
    }

    /**
     * Add multiple values to this tag.
     * @param ids The value IDs.
     * @return this
     */
    public TagBuilder values(Identifier... ids) {
        with("values", JsonArray::new, values -> { for(Identifier id : ids) values.add(id.toString()); });
        return this;
    }

    /**
     * Include another tag into this tag's values.
     * @param tagId The tag ID.
     * @return this
     */
    public TagBuilder include(Identifier tagId) {
        with("values", JsonArray::new, values -> values.add("#"+tagId.toString()));
        return this;
    }
}
