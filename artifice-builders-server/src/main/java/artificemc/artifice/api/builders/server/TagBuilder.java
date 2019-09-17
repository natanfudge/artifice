package artificemc.artifice.api.builders.server;

import artificemc.artifice.api.builders.TypedJsonBuilder;
import artificemc.artifice.api.core.resource.JsonResource;
import artificemc.artifice.impl.core.util.IdUtils;

/**
 * Builder for tag files ({@code namespace:tags/type/tagid.json}).
 * @see <a href="https://minecraft.gamepedia.com/Tag" target="_blank">Minecraft Wiki</a>
 */
public final class TagBuilder extends TypedJsonBuilder<JsonResource, TagBuilder> {
    public TagBuilder() { super(JsonResource::new); }

    /**
     * Set whether this tag should override or append to versions of the same tag in lower priority data packs.
     * @param replace Whether to replace.
     * @return this
     */
    public TagBuilder replace(boolean replace) {
        this.set("replace", replace);
        return this;
    }

    /**
     * Add a value to this tag.
     * @param id The value ID.
     * @return this
     */
    public TagBuilder value(String id) {
        IdUtils.validateIdentifier(id);
        withArray("values", values -> values.add(id));
        return this;
    }

    /**
     * Add multiple values to this tag.
     * @param ids The value IDs.
     * @return this
     */
    public TagBuilder values(String... ids) {
        withArray("values", values -> { 
            for (String id : ids) {
                IdUtils.validateIdentifier(id);
                values.add(id);
            }
        });
        return this;
    }

    /**
     * Include another tag into this tag's values.
     * @param tagId The tag ID.
     * @return this
     */
    public TagBuilder include(String tagId) {
        IdUtils.validateIdentifier(tagId);
        withArray("values", values -> values.add("#" + tagId));
        return this;
    }
}
