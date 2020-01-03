package com.swordglowsblue.artifice.common;

import net.minecraft.client.resource.ClientResourcePackProfile;
import net.minecraft.resource.ResourcePackProfile;

public interface ClientResourcePackProfileLike {
    <T extends ResourcePackProfile> ClientResourcePackProfile toClientResourcePackProfile(ResourcePackProfile.Factory<T> factory);
}
