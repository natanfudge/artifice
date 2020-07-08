package com.swordglowsblue.artifice.common;

import net.minecraft.resource.ResourcePackProfile;

public interface ClientResourcePackProfileLike {
    // Supplier to avoid loading ClientResourcePackProfile on the server
    <T extends ResourcePackProfile> ClientOnly<ResourcePackProfile> toClientResourcePackProfile(ResourcePackProfile.Factory factory);
}
