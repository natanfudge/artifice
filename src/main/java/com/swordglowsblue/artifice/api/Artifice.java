package com.swordglowsblue.artifice.api;

import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.util.Processor;
import com.swordglowsblue.artifice.common.ArtificeRegistry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

/** Registry methods for Artifice's virtual resource pack support. */
@SuppressWarnings("DeprecatedIsStillUsed")
public final class Artifice {
    private Artifice() {}

    /**
     * @deprecated Best to not use it at all, but if you need to use {@link com.swordglowsblue.artifice.common.ArtificeRegistry#ASSETS} instead
     * The {@link Registry} for client-side resource packs. */
    @Environment(EnvType.CLIENT)
    @Deprecated
    public static final MutableRegistry<ArtificeResourcePack> ASSETS = new SimpleRegistry<>();
    /**
     * @deprecated Best to not use it at all, but if you need to use {@link com.swordglowsblue.artifice.common.ArtificeRegistry#DATA} instead
     * The {@link Registry} for server-side resource packs. */
    @Deprecated
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
        return Registry.register(ArtificeRegistry.ASSETS, id, ArtificeResourcePack.ofAssets(register)); }

    /**
     * Register a new server-side resource pack, creating resources with the given callback.
     * @param id The pack ID.
     * @param register A callback which will be passed a {@link ServerResourcePackBuilder}.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofData
     */
    public static ArtificeResourcePack registerData(Identifier id, Processor<ServerResourcePackBuilder> register) {
        return Registry.register(ArtificeRegistry.DATA, id, ArtificeResourcePack.ofData(register)); }

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
        return Registry.register(ArtificeRegistry.ASSETS, id, pack); }

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
        return Registry.register(ArtificeRegistry.DATA, id, pack); }
}
