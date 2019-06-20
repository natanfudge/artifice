package com.swordglowsblue.artifice.api.resource;

import com.google.gson.JsonElement;
import org.apache.commons.io.input.ReaderInputStream;

import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class JsonResource<T extends JsonElement> implements ArtificeResource<T> {
    private final T root;
    public JsonResource(T root) { this.root = root; }

    public T getData() { return this.root; }
    public InputStream toInputStream() {
        return new ReaderInputStream(new StringReader(this.getData().toString()), StandardCharsets.UTF_8);
    }
}
