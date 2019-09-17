package artificemc.artifice.api.builders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonObjectBuilder extends TypedJsonBuilder<JsonObject, JsonObjectBuilder> {
    public JsonObjectBuilder() { super(r->r); }
    public JsonObjectBuilder(JsonObject root) { super(root, r->r); }

    public JsonObjectBuilder set(String key, String value) { return super.set(key, value); }
    public JsonObjectBuilder set(String key, Number value) { return super.set(key, value); }
    public JsonObjectBuilder set(String key, Boolean value) { return super.set(key, value); }
    public JsonObjectBuilder set(String key, Character value) { return super.set(key, value); }
    public JsonObjectBuilder set(String key, JsonElement value) { return super.set(key, value); }
}
