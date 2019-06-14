package com.swordglowsblue.artifice.impl.util;

import net.minecraft.util.Identifier;

public final class IdUtils {
    public static Identifier wrapPath(String prefix, Identifier id) { return wrapPath(prefix, id, ""); }
    public static Identifier wrapPath(Identifier id, String suffix) { return wrapPath("", id, suffix); }
    public static Identifier wrapPath(String prefix, Identifier id, String suffix) {
        return new Identifier(id.getNamespace(), prefix+id.getPath()+suffix);
    }

    public static Identifier withDefaultNamespace(Identifier id, String defaultNamespace) {
        return new Identifier(id.getNamespace().equals("minecraft") ? defaultNamespace : id.getNamespace(), id.getPath());
    }
}
