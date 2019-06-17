package com.swordglowsblue.artifice.mixin;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.impl.ArtificeResourcePackContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.ClientResourcePackContainer;
import net.minecraft.resource.*;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Shadow private ResourcePackContainerManager<ClientResourcePackContainer> resourcePackContainerManager;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void registerPackCreator(CallbackInfo cbi) {
        this.resourcePackContainerManager.addCreator(new ResourcePackCreator() {
            public <T extends ResourcePackContainer> void registerContainer(Map<String, T> packs, ResourcePackContainer.Factory<T> factory) {
                for(Identifier id : Artifice.ASSETS.getIds()) {
                    ArtificeResourcePack pack = Artifice.ASSETS.get(id);
                    T cont = ResourcePackContainer.of(id.toString(), false, () -> pack, factory,
                        pack.isOptional() ? ResourcePackContainer.InsertionPosition.TOP : ResourcePackContainer.InsertionPosition.BOTTOM);
                    packs.put(id.toString(), (T)new ArtificeResourcePackContainer(pack.isOptional(), cont));
                }
            }
        });
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void enableNonOptional(CallbackInfo cbi) {
        this.resourcePackContainerManager.getDisabledContainers().forEach(c -> {
            if(c instanceof ArtificeResourcePackContainer && !((ArtificeResourcePackContainer)c).isOptional())
                this.resourcePackContainerManager.getEnabledContainers().add(c);
        });
    }
}
