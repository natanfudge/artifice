package com.swordglowsblue.artifice.api.builder.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Identifier;

public final class AdvancementBuilder extends TypedJsonBuilder<JsonResource> {
    public AdvancementBuilder() { super(new JsonObject(), JsonResource::new); }

    public AdvancementBuilder display(Processor<Display> settings) {
        with("display", JsonObject::new, display ->
            settings.process(new Display()).buildTo(display)
        );
        return this;
    }

    public AdvancementBuilder parent(Identifier id) {
        root.addProperty("parent", id.toString());
        return this;
    }

    public AdvancementBuilder criteria(String name, Processor<Criteria> settings) {
        with("criteria", JsonObject::new, criteria -> with(name, JsonObject::new, criterion ->
            settings.process(new Criteria()).buildTo(criterion)
        ));
        return this;
    }

    public AdvancementBuilder requirement(String... anyOf) {
        with("requirements", JsonArray::new, requirements -> {
            JsonArray array = new JsonArray();
            for(String name : anyOf) array.add(name);
            requirements.add(array);
        });
        return this;
    }

    public static final class Display extends TypedJsonBuilder<JsonObject> {
        private Display() { super(new JsonObject(), j->j); }

        public Display icon(Identifier item) { return icon(item, null); }
        public Display icon(Identifier item, String nbt) {
            with("icon", JsonObject::new, icon -> {
               icon.addProperty("item", item.toString());
               if(nbt != null) icon.addProperty("nbt", nbt);
            });
            return this;
        }

        public Display title(String title) {
            root.addProperty("title", title);
            return this;
        }

        public Display title(Component title) {
            root.add("title", Component.Serializer.toJson(title));
            return this;
        }

        public Display frame(Frame frame) {
            root.addProperty("frame", frame.name);
            return this;
        }

        public Display background(Identifier id) {
            root.addProperty("background", id.toString());
            return this;
        }

        public Display description(String desc) {
            root.addProperty("description", desc);
            return this;
        }

        public Display description(Component desc) {
            root.add("description", Component.Serializer.toJson(desc));
            return this;
        }

        public Display showToast(boolean show) {
            root.addProperty("show_toast", show);
            return this;
        }

        public Display announceToChat(boolean announce) {
            root.addProperty("announce_to_chat", announce);
            return this;
        }

        public Display hidden(boolean hidden) {
            root.addProperty("hidden", hidden);
            return this;
        }

        public enum Frame {
            CHALLENGE("challenge"),
            GOAL("goal"),
            TASK("task");

            public final String name;
            Frame(String name) { this.name = name; }
        }
    }

    public static final class Criteria extends TypedJsonBuilder<JsonObject> {
        private Criteria() { super(new JsonObject(), j->j); }

        public Criteria trigger(Identifier id) {
            root.addProperty("trigger", id.toString());
            return this;
        }

        public Criteria conditions(Processor<JsonObjectBuilder> settings) {
            root.add("conditions", settings.process(new JsonObjectBuilder()).build());
            return this;
        }
    }
}
