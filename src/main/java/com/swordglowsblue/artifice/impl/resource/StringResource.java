package com.swordglowsblue.artifice.impl.resource;

import com.swordglowsblue.artifice.api.ArtificeResource;
import org.apache.commons.io.input.ReaderInputStream;

import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class StringResource implements ArtificeResource<String> {
    private final String data;
    public StringResource(String data) { this.data = data; }

    public String getData() { return this.data; }
    public InputStream toInputStream() {
        return new ReaderInputStream(new StringReader(this.data), StandardCharsets.UTF_8);
    }
}
