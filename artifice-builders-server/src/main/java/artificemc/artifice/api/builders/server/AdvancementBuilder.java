package artificemc.artifice.api.builders.server;

import artificemc.artifice.api.builders.JsonObjectBuilder;
import artificemc.artifice.api.builders.TypedJsonBuilder;
import artificemc.artifice.api.core.resource.JsonResource;
import artificemc.artifice.api.core.util.Processor;
import artificemc.artifice.impl.core.util.IdUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Builder for advancement files ({@code namespace:advancements/advid.json}).
 * @see <a href="https://minecraft.gamepedia.com/Advancements#JSON_Format" target="_blank">Minecraft Wiki</a>
 */
public final class AdvancementBuilder extends TypedJsonBuilder<JsonResource, AdvancementBuilder> {
    public AdvancementBuilder() { super(JsonResource::new); }

    /**
     * Set the display options for this advancement.
     * @param settings A callback which will be passed a {@link Display}.
     * @return this
     */
    public AdvancementBuilder display(Processor<Display> settings) {
        this.set("display", settings.process(new Display()).build());
        return this;
    }

    /**
     * Set the parent advancement for this to inherit from.
     * @param id The parent advancement ID ({@code namespace:advid}).
     * @return this
     */
    public AdvancementBuilder parent(String id) {
        IdUtils.validateIdentifier(id);
        this.set("parent", id);
        return this;
    }

    /**
     * Add a critera for this advancement to be received.
     * @param name The name of this criteria.
     * @param settings A callback which will be passed a {@link Criteria}.
     * @return this
     */
    public AdvancementBuilder criteria(String name, Processor<Criteria> settings) {
        withObject("criteria", criteria -> criteria.set(name, settings.process(new Criteria()).build()));
        return this;
    }

    /**
     * Set which criteria are required to receive this advancement.
     * Passing multiple critera names will allow the advancement to be received if any of the given critera are completed.
     * Calling this multiple times will add a new set of requirements. Each set must have at least one contained criteria completed
     *  to receive the advancement.
     * If this is not called, all criteria will be required by default.
     *
     * @param anyOf A list of criteria names, any of which can be completed to fulfill this requirement.
     * @return this
     */
    public AdvancementBuilder requirement(String... anyOf) {
        withArray("requirements", requirements -> {
            JsonArray array = new JsonArray();
            for(String name : anyOf) array.add(name);
            requirements.add(array);
        });
        return this;
    }

    /**
     * Builder for advancement display properties.
     * @see AdvancementBuilder
     */
    public static final class Display extends TypedJsonBuilder<JsonObject, Display> {
        private Display() { super(j->j); }

        /**
         * Set the icon item of this advancement.
         * @param item The item ID.
         * @return this
         */
        public Display icon(String item) {
            IdUtils.validateIdentifier(item);
            return icon(item, null);
        }

        /**
         * Set the icon item of this advancement.
         * @param item The item ID.
         * @param nbt A string containing the JSON-serialized NBT of the item.
         * @return this
         */
        public Display icon(String item, String nbt) {
            IdUtils.validateIdentifier(item);
            withObject("icon", icon -> {
                icon.set("item", item);
                if (nbt != null) icon.set("nbt", nbt);
            });
            return this;
        }

        /**
         * Set the title of this advancement.
         * @param title The title.
         * @return this
         */
        public Display title(String title) {
            this.set("title", title);
            return this;
        }

        /**
         * Set the title of this advancement as a JSON text component.
         * @param settings A callback which will be passed a {@link JsonObjectBuilder}.
         * @return this
         */
        public Display title(Processor<JsonObjectBuilder> settings) {
            this.set("title", settings.process(new JsonObjectBuilder()).build());
            return this;
        }

        /**
         * Set the frame type of this advancement.
         * @param frame The frame type.
         * @return this
         */
        public Display frame(Frame frame) {
            this.set("frame", frame.name);
            return this;
        }

        /**
         * Set the background texture of this advancement. Only applicable for root advancements.
         * @param id The texture path ({@code namespace:textures/gui/advancements/backgrounds/bgname.png}).
         * @return this
         */
        public Display background(String id) {
            IdUtils.validateIdentifier(id);
            this.set("background", id);
            return this;
        }

        /**
         * Set the description of this advancement.
         * @param desc The description.
         * @return this
         */
        public Display description(String desc) {
            this.set("description", desc);
            return this;
        }

        /**
         * Set the description of this advancement.
         * @param settings A callback which will be passed a {@link JsonObjectBuilder}.
         * @return this
         */
        public Display description(Processor<JsonObjectBuilder> settings) {
            this.set("description", settings.process(new JsonObjectBuilder()).build());
            return this;
        }

        /**
         * Set whether this advancement should show a popup onscreen when received.
         * @param show Whether to show the toast.
         * @return this
         */
        public Display showToast(boolean show) {
            this.set("show_toast", show);
            return this;
        }

        /**
         * Set whether achieving this advancement should post a message in chat.
         * @param announce Whether to announce.
         * @return this
         */
        public Display announceToChat(boolean announce) {
            this.set("announce_to_chat", announce);
            return this;
        }

        /**
         * Set whether this advancement should be hidden from the advancement menu until received.
         * @param hidden Whether to hide.
         * @return this
         */
        public Display hidden(boolean hidden) {
            this.set("hidden", hidden);
            return this;
        }

        /**
         * Options for {@link Display#frame}.
         */
        public enum Frame {
            CHALLENGE("challenge"),
            GOAL("goal"),
            TASK("task");

            /** The name of this frame when outputted to JSON. */
            public final String name;
            Frame(String name) { this.name = name; }
        }
    }

    /**
     * Builder for advancement criteria.
     * @see AdvancementBuilder
     * @see <a href="https://minecraft.gamepedia.com/Advancements#List_of_triggers" target="_blank">Minecraft Wiki</a>
     */
    public static final class Criteria extends TypedJsonBuilder<JsonObject, Criteria> {
        private Criteria() { super(j->j); }

        /**
         * Set the trigger condition of this criteria.
         * @param id The trigger ID ({@code namespace:triggerid}).
         * @return this
         * @see <a href="https://minecraft.gamepedia.com/Advancements#List_of_triggers" target="_blank">Minecraft Wiki</a>
         */
        public Criteria trigger(String id) {
            IdUtils.validateIdentifier(id);
            this.set("trigger", id);
            return this;
        }

        /**
         * Set the condition values for the given trigger.
         * These vary from trigger to trigger, so this falls through to direct JSON building.
         *
         * @param settings A callback which will be passed a {@link JsonObjectBuilder}.
         * @return this
         * @see <a href="https://minecraft.gamepedia.com/Advancements#List_of_triggers" target="_blank">Minecraft Wiki</a>
         */
        public Criteria conditions(Processor<JsonObjectBuilder> settings) {
            this.set("conditions", settings.process(new JsonObjectBuilder()).build());
            return this;
        }
    }
}
