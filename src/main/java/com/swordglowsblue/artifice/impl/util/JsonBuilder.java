package com.swordglowsblue.artifice.impl.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class JsonBuilder<T> {
    protected final JsonObject root;
    private final Function<JsonObject, T> ctor;

    protected JsonBuilder(JsonObject root, Function<JsonObject, T> ctor) {
        this.root = root;
        this.ctor = ctor;
    }

    public T build() { return buildTo(new JsonObject()); }
    public T buildTo(JsonObject target) {
        root.entrySet().forEach(e -> target.add(e.getKey(), e.getValue()));
        return ctor.apply(target);
    }

    protected <J extends JsonElement> void with(String key, Supplier<J> ctor, Consumer<J> run) { with(root, key, ctor, run); }

    @SuppressWarnings("unchecked")
    protected static <J extends JsonElement> void with(JsonObject in, String key, Supplier<J> ctor, Consumer<J> run) {
        J elem = in.has(key) ? (J)in.get(key) : ctor.get();
        run.accept(elem);
        in.add(key, elem);
    }

    @SuppressWarnings("unchecked")
    protected static <J extends JsonElement> void with(JsonArray in, String key, String sep, Supplier<J> ctor, Consumer<J> run) {
        int keyInt = Integer.parseInt(key);
        J elem = in.size() > keyInt ? (J)in.get(keyInt) : ctor.get();
        run.accept(elem);
        in.set(keyInt, elem);
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
