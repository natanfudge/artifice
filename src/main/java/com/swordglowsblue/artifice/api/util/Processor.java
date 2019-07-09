package com.swordglowsblue.artifice.api.util;

import java.util.function.Consumer;

/** A wrapper around {@link Consumer} that returns its argument instead of {@code void}.
 *  @param <T> The type to be passed in and returned. */
@FunctionalInterface
public interface Processor<T> extends Consumer<T> {
    default T process(T t) {
        this.accept(t);
        return t;
    }
}

