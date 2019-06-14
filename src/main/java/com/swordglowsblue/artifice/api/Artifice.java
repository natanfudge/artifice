package com.swordglowsblue.artifice.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

public final class Artifice {
    private Artifice() {}

    @Environment(EnvType.CLIENT)
    public static final MutableRegistry<ResourcePack> ASSETS = new SimpleRegistry<>();
    public static final MutableRegistry<ResourcePack> DATA = new SimpleRegistry<>();

    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(String id, ArtificeResourcePack pack) { return registerAssets(new Identifier(id), pack); }
    public static ArtificeResourcePack registerData(String id, ArtificeResourcePack pack) { return registerData(new Identifier(id), pack); }

    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(Identifier id, ArtificeResourcePack pack) {
        if(pack.getType() != ResourceType.CLIENT_RESOURCES) throw new IllegalArgumentException("Cannot register a data pack as a resource pack.");
        return Registry.register(ASSETS, id, pack);
    }

    public static ArtificeResourcePack registerData(Identifier id, ArtificeResourcePack pack) {
        if(pack.getType() != ResourceType.SERVER_DATA) throw new IllegalArgumentException("Cannot register a resource pack as a data pack.");
        return Registry.register(DATA, id, pack);
    }
}
