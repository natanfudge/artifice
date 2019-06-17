package com.swordglowsblue.artifice.api;

import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourceRegistry;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourceRegistry;
import com.swordglowsblue.artifice.impl.ArtificeResourcePackImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

import java.util.function.Consumer;

public final class Artifice {
    private Artifice() {}

    @Environment(EnvType.CLIENT)
    public static final MutableRegistry<ArtificeResourcePack> ASSETS =
        Registry.register(Registry.REGISTRIES, "artifice:assets_packs", new SimpleRegistry<>());
    public static final MutableRegistry<ArtificeResourcePack> DATA =
        Registry.register(Registry.REGISTRIES, "artifice:data_packs", new SimpleRegistry<>());

    @Environment(EnvType.CLIENT)
    public static void registerAssets(String id, Consumer<ClientResourceRegistry> register) { registerAssets(new Identifier(id), register); }
    public static void registerData(String id, Consumer<ServerResourceRegistry> register) { registerData(new Identifier(id), register); }

    @Environment(EnvType.CLIENT)
    public static void registerAssets(Identifier id, Consumer<ClientResourceRegistry> register) {
        Registry.register(ASSETS, id, new ArtificeResourcePackImpl(ResourceType.CLIENT_RESOURCES, register));
    }

    public static void registerData(Identifier id, Consumer<ServerResourceRegistry> register) {
        Registry.register(DATA, id, new ArtificeResourcePackImpl(ResourceType.SERVER_DATA, register));
    }
}
