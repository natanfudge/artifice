package com.swordglowsblue.artifice.impl;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class ArtificeImpl {

    private static final Logger log4jLogger = LogManager.getLogger("Artifice");
    public static final Logger LOGGER = log4jLogger;

    public static <V, T extends V> T registerSafely(Registry<V> registry, Identifier id, T entry) {
        return Registry.register(registry, id, entry);
    }
}
