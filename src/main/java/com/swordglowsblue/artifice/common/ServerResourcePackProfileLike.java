package com.swordglowsblue.artifice.common;

import net.minecraft.resource.ResourcePackProfile;

public interface ServerResourcePackProfileLike {
    <T extends ResourcePackProfile> ResourcePackProfile toServerResourcePackProfile(ResourcePackProfile.Factory factory);
}
