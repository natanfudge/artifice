package artificemc.artifice.api.core.resource;

import artificemc.artifice.impl.core.util.StringInputStream;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.InputStream;

public class JsonResource implements Resource<JsonObject> {
    private final JsonObject data;
    public JsonResource(JsonObject data) { this.data = data; }

    public JsonObject getData() { return data.deepCopy(); }
    public String toOutputString() { return new GsonBuilder().setPrettyPrinting().create().toJson(data); }
    public InputStream toInputStream() { return StringInputStream.from(data); }
}
