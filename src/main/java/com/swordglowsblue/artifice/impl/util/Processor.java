package com.swordglowsblue.artifice.impl.util;

@FunctionalInterface
public interface Processor<T> {
    T process(T t);
}

