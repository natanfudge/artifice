package com.swordglowsblue.artifice.common;

import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ArtificeRegistry {
    /**
     * The {@link Registry} for client-side resource packs.
     */
    @Environment(EnvType.CLIENT)
    public static final MutableRegistry<ClientResourcePackProfileLike> ASSETS = new SimpleRegistry<>();
    /**
     * The {@link Registry} for server-side resource packs.
     */
    public static final MutableRegistry<ServerResourcePackProfileLike> DATA =
                    Registry.register(Registry.REGISTRIES, "artifice:common_data_packs", new SimpleRegistry<>());
}
