package artificemc.artifice.api.builders;

import artificemc.artifice.api.core.resource.ResourceTemplate;
import artificemc.artifice.api.core.util.Processor;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.function.Function;

@SuppressWarnings("unchecked")
public abstract class TypedJsonBuilder<T, B extends TypedJsonBuilder<T, B>> {
    protected final JsonObject root;
    private final Function<JsonObject, T> constr;

    protected TypedJsonBuilder(Function<JsonObject, T> constr) { this(new JsonObject(), constr); }
    protected TypedJsonBuilder(JsonObject root, Function<JsonObject, T> constr) {
        this.root = root;
        this.constr = constr;
    }

    public final T build() { return constr.apply(root); }

    public final ResourceTemplate toTemplate(String namespace, String pathTemplate) {
        return new ResourceTemplate(namespace, pathTemplate, build().toString());
    }

    protected B set(String key, String value) { root.addProperty(key, value); return (B)this; }
    protected B set(String key, Number value) { root.addProperty(key, value); return (B)this; }
    protected B set(String key, Boolean value) { root.addProperty(key, value); return (B)this; }
    protected B set(String key, Character value) { root.addProperty(key, value); return (B)this; }
    protected B set(String key, JsonElement value) { root.add(key, value); return (B)this; }

    protected final void withObject(String key, Processor<JsonObjectBuilder> f) {
        this.withObject(root, key, f); }
    protected final void withObject(JsonObject root, String key, Processor<JsonObjectBuilder> f) {
        JsonObject with = root.getAsJsonObject(key);
        if(with != null) f.process(new JsonObjectBuilder(with));
        else root.add(key, f.process(new JsonObjectBuilder()).build());
    }

    protected final void withArray(String key, Processor<JsonArrayBuilder> f) {
        this.withArray(root, key, f); }
    protected final void withArray(JsonObject root, String key, Processor<JsonArrayBuilder> f) {
        JsonArray with = root.getAsJsonArray(key);
        if(with != null) f.process(new JsonArrayBuilder(with));
        else root.add(key, f.process(new JsonArrayBuilder()).build());
    }

    protected final JsonArray arrayOf(String... values) {
        JsonArray array = new JsonArray();
        for(String i : values) array.add(i);
        return array;
    }

    protected final JsonArray arrayOf(Number... values) {
        JsonArray array = new JsonArray();
        for(Number i : values) array.add(i);
        return array;
    }

    protected final JsonArray arrayOf(Boolean... values) {
        JsonArray array = new JsonArray();
        for(Boolean i : values) array.add(i);
        return array;
    }

    protected final JsonArray arrayOf(Character... values) {
        JsonArray array = new JsonArray();
        for(Character i : values) array.add(i);
        return array;
    }

    protected final JsonArray arrayOf(JsonElement... values) {
        JsonArray array = new JsonArray();
        for(JsonElement i : values) array.add(i);
        return array;
    }
}
