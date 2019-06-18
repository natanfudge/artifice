package com.swordglowsblue.artifice.api.util;

import java.util.function.Consumer;

@FunctionalInterface
public interface Processor<T> extends Consumer<T> {
    default T process(T t) {
        this.accept(t);
        return t;
    }
}

