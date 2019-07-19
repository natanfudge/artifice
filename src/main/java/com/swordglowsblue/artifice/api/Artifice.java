package com.swordglowsblue.artifice.api;

import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

/** Registry methods for Artifice's virtual resource pack support. */
public final class Artifice {
    private Artifice() {}

    /** The {@link Registry} for client-side resource packs. */
    @Environment(EnvType.CLIENT)
    public static final MutableRegistry<ArtificeResourcePack> ASSETS =
        Registry.register(Registry.REGISTRIES, "artifice:assets_packs", new SimpleRegistry<>());
    /** The {@link Registry} for server-side resource packs. */
    public static final MutableRegistry<ArtificeResourcePack> DATA =
        Registry.register(Registry.REGISTRIES, "artifice:data_packs", new SimpleRegistry<>());

    /**
     * Register a new client-side resource pack, creating resources with the given callback.
     * @param id The pack ID.
     * @param register A callback which will be passed a {@link ClientResourcePackBuilder}.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofAssets
     */
    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(String id, Processor<ClientResourcePackBuilder> register) {
        return registerAssets(new Identifier(id), register); }

    /**
     * Register a new server-side resource pack, creating resources with the given callback.
     * @param id The pack ID.
     * @param register A callback which will be passed a {@link ServerResourcePackBuilder}.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofData
     */
    public static ArtificeResourcePack registerData(String id, Processor<ServerResourcePackBuilder> register) {
        return registerData(new Identifier(id), register); }

    /**
     * Register a new client-side resource pack, creating resources with the given callback.
     * @param id The pack ID.
     * @param register A callback which will be passed a {@link ClientResourcePackBuilder}.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofAssets
     */
    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(Identifier id, Processor<ClientResourcePackBuilder> register) {
        return Registry.register(ASSETS, id, ArtificeResourcePack.ofAssets(register)); }

    /**
     * Register a new server-side resource pack, creating resources with the given callback.
     * @param id The pack ID.
     * @param register A callback which will be passed a {@link ServerResourcePackBuilder}.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofData
     */
    public static ArtificeResourcePack registerData(Identifier id, Processor<ServerResourcePackBuilder> register) {
        return Registry.register(DATA, id, ArtificeResourcePack.ofData(register)); }

    /**
     * Register a new client-side resource pack.
     * @param id The pack ID.
     * @param pack The pack to register.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofAssets
     */
    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(String id, ArtificeResourcePack pack) {
        return registerAssets(new Identifier(id), pack); }

    /**
     * Register a new server-side resource pack.
     * @param id The pack ID.
     * @param pack The pack to register.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofData
     */
    public static ArtificeResourcePack registerData(String id, ArtificeResourcePack pack) {
        return registerData(new Identifier(id), pack); }

    /**
     * Register a new client-side resource pack.
     * @param id The pack ID.
     * @param pack The pack to register.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofAssets
     */
    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(Identifier id, ArtificeResourcePack pack) {
        if(pack.getType() != ResourceType.CLIENT_RESOURCES)
            throw new IllegalArgumentException("Cannot register a server-side pack as assets");
        return Registry.register(ASSETS, id, pack); }

    /**
     * Register a new server-side resource pack.
     * @param id The pack ID.
     * @param pack The pack to register.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofData
     */
    public static ArtificeResourcePack registerData(Identifier id, ArtificeResourcePack pack) {
        if(pack.getType() != ResourceType.SERVER_DATA)
            throw new IllegalArgumentException("Cannot register a client-side pack as data");
        return Registry.register(DATA, id, pack); }
}
