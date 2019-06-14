package com.swordglowsblue.artifice.api;

import java.io.InputStream;

public interface ArtificeResource<T> {
    T getData();
    InputStream toInputStream();
}
