package com.swordglowsblue.artifice.api.util;

import net.minecraft.util.Identifier;

/** Utilities for modifying {@link Identifier}s. */
public final class IdUtils {
    /**
     * Add a prefix to the path of the given {@link Identifier}.
     * @param prefix The prefix to add.
     * @param id The base ID.
     * @return A new {@link Identifier} with the prefixed path.
     */
    public static Identifier wrapPath(String prefix, Identifier id) { return wrapPath(prefix, id, ""); }

    /**
     * Add a suffix to the path of the given {@link Identifier}.
     * @param id The base ID.
     * @param suffix The suffix to add.
     * @return A new {@link Identifier} with the suffixed path.
     */
    public static Identifier wrapPath(Identifier id, String suffix) { return wrapPath("", id, suffix); }

    /**
     * Add a prefix and suffix to the path of the given {@link Identifier}.
     * @param prefix The prefix to add.
     * @param id The base ID.
     * @param suffix The suffix to add.
     * @return A new {@link Identifier} with the wrapped path.
     */
    public static Identifier wrapPath(String prefix, Identifier id, String suffix) {
        if(prefix.isEmpty() && suffix.isEmpty()) return id;
        return new Identifier(id.getNamespace(), prefix+id.getPath()+suffix);
    }

    /**
     * If the given {@link Identifier} has the namespace "minecraft" (the default namespace),
     *   return a copy with the given {@code defaultNamespace}. Otherwise, return the ID unchanged.
     * @param id The base ID.
     * @param defaultNamespace The namespace to replace {@code minecraft} with if applicable.
     * @return The given ID with its namespace replaced if applicable.
     */
    public static Identifier withDefaultNamespace(Identifier id, String defaultNamespace) {
        return id.getNamespace().equals("minecraft") ? new Identifier(defaultNamespace, id.getPath()) : id;
    }
}
