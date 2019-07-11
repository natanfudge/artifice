package com.swordglowsblue.artifice.api.resource;

import java.io.InputStream;

/** A virtual resource file. */
public interface ArtificeResource<T> {
    /** @return The raw data contained by this resource file. */
    T getData();
    /** @return The output-formatted string representation of this resource's data. */
    String toOutputString();
    /** @return This resource converted to an {@link InputStream}. */
    InputStream toInputStream();
}
