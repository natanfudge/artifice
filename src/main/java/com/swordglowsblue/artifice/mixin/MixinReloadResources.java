package com.swordglowsblue.artifice.mixin;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.impl.ArtificeInit;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceReloadMonitor;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableResourceManagerImpl.class)
public abstract class MixinReloadResources {
    @Shadow private ResourceType type;
    @Shadow public abstract void addPack(ResourcePack pack);

    @Inject(method = "<init>", at = @At("RETURN"))
    private void loadCustomResourcePacks(CallbackInfo cbi) {
        switch(this.type) {
            case CLIENT_RESOURCES: Artifice.ASSETS.forEach(this::addPack); break;
            case SERVER_DATA: Artifice.DATA.forEach(this::addPack); break;
        }
    }

    @Inject(method = "beginMonitoredReload", at = @At("HEAD"))
    private void markCustomResourcePacksForReload(
        Executor ex1, Executor ex2, CompletableFuture<Unit> f, List<ResourcePack> packs,
        CallbackInfoReturnable<ResourceReloadMonitor> cbi
    ) {
        switch(this.type) {
            case CLIENT_RESOURCES: Artifice.ASSETS.forEach(p -> packs.add(1, p)); break;
            case SERVER_DATA: Artifice.DATA.forEach(p -> packs.add(1, p)); break;
        }
    }
}
