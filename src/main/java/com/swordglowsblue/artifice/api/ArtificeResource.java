package com.swordglowsblue.artifice.api;

import com.google.gson.JsonObject;
import org.apache.commons.io.input.ReaderInputStream;

import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public interface ArtificeResource {
    JsonObject toJson();

    default InputStream toInputStream() {
        return new ReaderInputStream(new StringReader(this.toJson().toString()), StandardCharsets.UTF_8);
    }
}
