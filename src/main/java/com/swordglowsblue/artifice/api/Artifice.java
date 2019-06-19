package com.swordglowsblue.artifice.api;

import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

public final class Artifice {
    private Artifice() {}

    @Environment(EnvType.CLIENT)
    public static final MutableRegistry<ArtificeResourcePack> ASSETS =
        Registry.register(Registry.REGISTRIES, "artifice:assets_packs", new SimpleRegistry<>());
    public static final MutableRegistry<ArtificeResourcePack> DATA =
        Registry.register(Registry.REGISTRIES, "artifice:data_packs", new SimpleRegistry<>());

    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(String id, Processor<ClientResourcePackBuilder> register) {
        return registerAssets(new Identifier(id), register); }
    public static ArtificeResourcePack registerData(String id, Processor<ServerResourcePackBuilder> register) {
        return registerData(new Identifier(id), register); }

    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(Identifier id, Processor<ClientResourcePackBuilder> register) {
        return Registry.register(ASSETS, id, ArtificeResourcePack.ofAssets(register)); }
    public static ArtificeResourcePack registerData(Identifier id, Processor<ServerResourcePackBuilder> register) {
        return Registry.register(DATA, id, ArtificeResourcePack.ofData(register)); }

    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(String id, ArtificeResourcePack pack) {
        return registerAssets(new Identifier(id), pack); }
    public static ArtificeResourcePack registerData(String id, ArtificeResourcePack pack) {
        return registerData(new Identifier(id), pack); }

    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(Identifier id, ArtificeResourcePack pack) {
        return Registry.register(ASSETS, id, pack); }
    public static ArtificeResourcePack registerData(Identifier id, ArtificeResourcePack pack) {
        return Registry.register(ASSETS, id, pack); }
}
