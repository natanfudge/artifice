package com.swordglowsblue.artifice.api;

import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.util.Processor;
import com.swordglowsblue.artifice.common.ArtificeRegistry;
import com.swordglowsblue.artifice.impl.ArtificeImpl;
import com.swordglowsblue.artifice.impl.DynamicResourcePackFactory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Registry methods for Artifice's virtual resource pack support.
 */
public final class Artifice {
    private Artifice() {
    }

    /**
     * Register a new client-side resource pack, creating resources with the given callback.
     *
     * @param id       The pack ID.
     * @param register A callback which will be passed a {@link ClientResourcePackBuilder}.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofAssets
     */
    @Deprecated
    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(String id, Processor<ClientResourcePackBuilder> register) {
        return registerAssets(new Identifier(id), register);
    }

    /**
     * Register a new server-side resource pack, creating resources with the given callback.
     *
     * @param id       The pack ID.
     * @param register A callback which will be passed a {@link ServerResourcePackBuilder}.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofData
     */
    @Deprecated
    public static ArtificeResourcePack registerData(String id, Processor<ServerResourcePackBuilder> register) {
        return registerData(new Identifier(id), register);
    }

    /**
     * Register a new client-side resource pack, creating resources with the given callback.
     *
     * @param id       The pack ID.
     * @param register A callback which will be passed a {@link ClientResourcePackBuilder}.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofAssets
     */
    @Deprecated
    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(Identifier id, Processor<ClientResourcePackBuilder> register) {
        return ArtificeImpl.registerSafely(ArtificeRegistry.ASSETS, id, ArtificeResourcePack.ofAssets(register));
    }

    /**
     * Register a new server-side resource pack, creating resources with the given callback.
     *
     * @param id       The pack ID.
     * @param register A callback which will be passed a {@link ServerResourcePackBuilder}.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofData
     */
    @Deprecated
    public static ArtificeResourcePack registerData(Identifier id, Processor<ServerResourcePackBuilder> register) {
        return ArtificeImpl.registerSafely(ArtificeRegistry.DATA, id, ArtificeResourcePack.ofData(register));
    }

    /**
     * Register a new client-side resource pack, creating resources with the given callback.
     *
     * @param id       The pack ID.
     * @param register A callback which will be passed a {@link ClientResourcePackBuilder}.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofAssets
     */
    @Environment(EnvType.CLIENT)
    public static void registerAssetPack(String id, Processor<ClientResourcePackBuilder> register) {
        registerAssetPack(new Identifier(id), register);
    }

    /**
     * Register a new server-side resource pack, creating resources with the given callback.
     *
     * @param id       The pack ID.
     * @param register A callback which will be passed a {@link ServerResourcePackBuilder}.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofData
     */
    public static void registerDataPack(String id, Processor<ServerResourcePackBuilder> register) {
        registerDataPack(new Identifier(id), register);
    }

    /**
     * Register a new client-side resource pack, creating resources with the given callback.
     *
     * @param id       The pack ID.
     * @param register A callback which will be passed a {@link ClientResourcePackBuilder}.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofAssets
     */
    @Environment(EnvType.CLIENT)
    public static void registerAssetPack(Identifier id, Processor<ClientResourcePackBuilder> register) {
        ArtificeImpl.registerSafely(ArtificeRegistry.ASSETS, id, new DynamicResourcePackFactory<>(ResourceType.CLIENT_RESOURCES, id, register));
    }


    /**
     * Register a new server-side resource pack, creating resources with the given callback.
     *
     * @param id       The pack ID.
     * @param register A callback which will be passed a {@link ServerResourcePackBuilder}.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofData
     */
    public static void registerDataPack(Identifier id, Processor<ServerResourcePackBuilder> register) {
        ArtificeImpl.registerSafely(ArtificeRegistry.DATA, id, new DynamicResourcePackFactory<>(ResourceType.SERVER_DATA, id, register));
    }

    /**
     * Register a new client-side resource pack.
     *
     * @param id   The pack ID.
     * @param pack The pack to register.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofAssets
     */
    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(String id, ArtificeResourcePack pack) {
        return registerAssets(new Identifier(id), pack);
    }

    /**
     * Register a new server-side resource pack.
     *
     * @param id   The pack ID.
     * @param pack The pack to register.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofData
     */
    public static ArtificeResourcePack registerData(String id, ArtificeResourcePack pack) {
        return registerData(new Identifier(id), pack);
    }

    /**
     * Register a new client-side resource pack.
     *
     * @param id   The pack ID.
     * @param pack The pack to register.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofAssets
     */
    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(Identifier id, ArtificeResourcePack pack) {
        if (pack.getType() != ResourceType.CLIENT_RESOURCES)
            throw new IllegalArgumentException("Cannot register a server-side pack as assets");
        return ArtificeImpl.registerSafely(ArtificeRegistry.ASSETS, id, pack);
    }

    /**
     * Register a new server-side resource pack.
     *
     * @param id   The pack ID.
     * @param pack The pack to register.
     * @return The registered pack.
     * @see ArtificeResourcePack#ofData
     */
    public static ArtificeResourcePack registerData(Identifier id, ArtificeResourcePack pack) {
        if (pack.getType() != ResourceType.SERVER_DATA)
            throw new IllegalArgumentException("Cannot register a client-side pack as data");
        return ArtificeImpl.registerSafely(ArtificeRegistry.DATA, id, pack);
    }
}
