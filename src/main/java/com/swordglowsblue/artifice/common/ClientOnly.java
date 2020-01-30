package com.swordglowsblue.artifice.common;

/**
 * Wrapper around some EnvType.CLIENT object, to avoid directly referencing it in runtime
 * (the generic type parameter is removed at runtime, avoiding referencing the client-only class)
 * @param <T> Some class marked with @Environment(EnvType.CLIENT)
 */
public class ClientOnly<T> {
    private final T clientOnly;

    public ClientOnly(T clientOnly) {
        this.clientOnly = clientOnly;
    }

    public T get() {
        return clientOnly;
    }
}
