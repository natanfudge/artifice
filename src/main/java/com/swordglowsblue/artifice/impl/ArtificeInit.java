package com.swordglowsblue.artifice.impl;

import com.swordglowsblue.artifice.api.Artifice;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;

public final class ArtificeInit implements ModInitializer, ClientModInitializer {
    public void onInitialize() {
        Registry.register(Registry.REGISTRIES, "artifice:data_packs", Artifice.DATA);
    }

    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        Registry.register(Registry.REGISTRIES, "artifice:assets_packs", Artifice.ASSETS);
    }
}
