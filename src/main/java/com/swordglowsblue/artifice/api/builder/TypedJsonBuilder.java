package com.swordglowsblue.artifice.api.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.util.Processor;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class TypedJsonBuilder<T> {
    protected final JsonObject root;
    private final Function<JsonObject, T> ctor;

    protected TypedJsonBuilder(JsonObject root, Function<JsonObject, T> ctor) {
        this.root = root;
        this.ctor = ctor;
    }

    public T build() { return buildTo(new JsonObject()); }
    public T buildTo(JsonObject target) {
        root.entrySet().forEach(e -> target.add(e.getKey(), e.getValue()));
        return ctor.apply(target);
    }

    @SuppressWarnings("unchecked")
    protected  <J extends JsonElement> void with(JsonObject in, String key, Supplier<J> ctor, Processor<J> run) {
        in.add(key, run.process(in.has(key) ? (J)in.get(key) : ctor.get())); }


    public <J extends JsonElement> void with(String key, Supplier<J> ctor, Processor<J> run) {
        this.with(root, key, ctor, run);
    }

    public TypedJsonBuilder<T> jsonElement(String name, JsonElement value) {
        root.add(name, value);
        return this;
    }

    public TypedJsonBuilder<T> jsonString(String name, String value) {
        root.addProperty(name, value);
        return this;
    }

    public TypedJsonBuilder<T> jsonBoolean(String name, boolean value) {
        root.addProperty(name, value);
        return this;
    }

    public TypedJsonBuilder<T> jsonNumber(String name, Number value) {
        root.addProperty(name, value);
        return this;
    }

    public TypedJsonBuilder<T> jsonChar(String name, Character value) {
        root.addProperty(name, value);
        return this;
    }

    public TypedJsonBuilder<T> jsonObject(String name, Processor<JsonObjectBuilder> settings) {
        root.add(name, settings.process(new JsonObjectBuilder()).build());
        return this;
    }

    public TypedJsonBuilder<T> jsonArray(String name, Processor<JsonArrayBuilder> settings) {
        root.add(name, settings.process(new JsonArrayBuilder()).build());
        return this;
    }

    protected JsonArray arrayOf(boolean... values) {
        JsonArray array = new JsonArray();
        for(boolean i : values) array.add(i);
        return array;
    }

    protected JsonArray arrayOf(Character... values) {
        JsonArray array = new JsonArray();
        for(Character i : values) array.add(i);
        return array;
    }

    protected JsonArray arrayOf(Number... values) {
        JsonArray array = new JsonArray();
        for(Number i : values) array.add(i);
        return array;
    }

    protected JsonArray arrayOf(String... values) {
        JsonArray array = new JsonArray();
        for(String i : values) array.add(i);
        return array;
    }
}
