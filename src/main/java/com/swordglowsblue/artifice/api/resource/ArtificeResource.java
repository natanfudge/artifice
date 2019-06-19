package com.swordglowsblue.artifice.api.resource;

import java.io.InputStream;

public interface ArtificeResource<T> {
    T getData();
    InputStream toInputStream();
}
