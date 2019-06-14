package com.swordglowsblue.artifice.impl;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.test.ArtificeTest;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.registry.Registry;

public final class ArtificeInit implements ModInitializer, ClientModInitializer {
    public void onInitialize() {
        Registry.register(Registry.REGISTRIES, "artifice:data_packs", Artifice.DATA);
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) ArtificeTest.onInitialize();
    }

    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        Registry.register(Registry.REGISTRIES, "artifice:assets_packs", Artifice.ASSETS);
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) ArtificeTest.onInitializeClient();
    }
}
