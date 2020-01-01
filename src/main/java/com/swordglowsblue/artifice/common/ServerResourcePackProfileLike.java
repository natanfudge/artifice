package com.swordglowsblue.artifice.common;

import net.minecraft.resource.ResourcePackProfile;

public interface ServerResourcePackProfileLike {
    ResourcePackProfile toServerResourcePackProfile(ResourcePackProfile.Factory<?> factory);
}
