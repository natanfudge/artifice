package artificemc.artifice.api.builders;

import artificemc.artifice.api.core.util.Processor;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class JsonArrayBuilder extends TypedJsonBuilder<JsonArray, JsonArrayBuilder> {
    public JsonArrayBuilder() { this(new JsonArray()); }
    public JsonArrayBuilder(JsonArray root) { super(
        new JsonObjectBuilder().set("values", root).build(),
        j -> j.getAsJsonArray("values")
    );}

    public JsonArrayBuilder add(String value) { root.getAsJsonArray("values").add(value); return this; }
    public JsonArrayBuilder add(Number value) { root.getAsJsonArray("values").add(value); return this; }
    public JsonArrayBuilder add(Boolean value) { root.getAsJsonArray("values").add(value); return this; }
    public JsonArrayBuilder add(Character value) { root.getAsJsonArray("values").add(value); return this; }
    public JsonArrayBuilder add(JsonElement value) { root.getAsJsonArray("values").add(value); return this; }

    public JsonArrayBuilder addObject(Processor<JsonObjectBuilder> settings) {
        root.getAsJsonArray("values").add(settings.process(new JsonObjectBuilder()).build());
        return this;
    }

    public JsonArrayBuilder addArray(Processor<JsonArrayBuilder> settings) {
        root.getAsJsonArray("values").add(settings.process(new JsonArrayBuilder()).build());
        return this;
    }
}
