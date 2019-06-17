package com.swordglowsblue.artifice.impl.resource;

import com.swordglowsblue.artifice.api.ArtificeResource;
import net.minecraft.client.resource.language.LanguageDefinition;
import org.apache.commons.io.input.NullInputStream;

import java.io.InputStream;

public class LanguageResource implements ArtificeResource<LanguageDefinition> {
    private LanguageDefinition language;
    public LanguageResource(LanguageDefinition language) { this.language = language; }

    public LanguageDefinition getData() { return language; }
    public InputStream toInputStream() { return new NullInputStream(0); }
}
