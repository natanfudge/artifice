package com.swordglowsblue.artifice.mixin;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.resource.ResourcePackContainer;
import net.minecraft.resource.ResourcePackContainerManager;
import net.minecraft.resource.ResourcePackCreator;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {
    @Shadow private ResourcePackContainerManager<ResourcePackContainer> dataPackContainerManager;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void registerPackCreator(CallbackInfo cbi) {
        this.dataPackContainerManager.addCreator(new ResourcePackCreator() {
            public <T extends ResourcePackContainer> void registerContainer(Map<String, T> packs, ResourcePackContainer.Factory<T> factory) {
                for(Identifier id : Artifice.DATA.getIds()) {
                    ArtificeResourcePack pack = Artifice.DATA.get(id);
                    T cont = ResourcePackContainer.of(id.toString(), false, () -> pack, factory, ResourcePackContainer.InsertionPosition.BOTTOM);
                    packs.put(id.toString(), cont);
                }
            }
        });
    }
}
