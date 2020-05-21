package com.swordglowsblue.artifice.common;

import com.mojang.serialization.Lifecycle;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ArtificeRegistry {
    /**
     * The {@link Registry} for client-side resource packs.
     */
    @Environment(EnvType.CLIENT)
    public static final MutableRegistry<ClientResourcePackProfileLike> ASSETS = new SimpleRegistry<>(
                    RegistryKey.getOrCreateRootKey(new Identifier("artifice", "common_assets")),
                    Lifecycle.stable()
    );
    /**
     * The {@link Registry} for server-side resource packs.
     */
    public static final MutableRegistry<ServerResourcePackProfileLike> DATA = Registry.register((Registry) Registry.REGISTRIES,
                    new Identifier("artifice", "common_data_packs"), new SimpleRegistry<>(
                    RegistryKey.getOrCreateRootKey(new Identifier("artifice", "data")),
                    Lifecycle.stable()
    ));
}
