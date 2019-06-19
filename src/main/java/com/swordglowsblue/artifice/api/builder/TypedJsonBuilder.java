package com.swordglowsblue.artifice.api.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.util.Processor;

import java.util.function.Consumer;
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
    protected <J extends JsonElement> void with(JsonObject in, String key, Supplier<J> ctor, Processor<J> run) {
        in.add(key, run.process(in.has(key) ? (J)in.get(key) : ctor.get())); }
    protected <J extends JsonElement> void with(String key, Supplier<J> ctor, Processor<J> run) {
        this.with(root, key, ctor, run); }

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
