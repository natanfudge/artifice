package com.swordglowsblue.artifice.common;

import net.minecraft.client.resource.ClientResourcePackProfile;
import net.minecraft.resource.ResourcePackProfile;

public interface ClientResourcePackProfileLike {
    ClientResourcePackProfile toClientResourcePackProfile(ResourcePackProfile.Factory<?> factory);
}
