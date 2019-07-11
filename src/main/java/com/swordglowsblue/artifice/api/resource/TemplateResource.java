package com.swordglowsblue.artifice.api.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/** A virtual resource representing a string with template expansions.
 *  Templates in the string take the form {@code $key}.
 *  @see TemplateResource#expand */
public class TemplateResource implements ArtificeResource<String> {
    private final String template;
    private final Map<String, String> expansions = new HashMap<>();

    /** @param template Individual lines of the template string. */
    public TemplateResource(String... template) { this.template = String.join("\n", template); }

    /**
     * Set the expansion string for a given key.
     * @param key The key to be expanded (ex. {@code "key"} expands {@code $key}).
     * @param expansion The expanded string.
     * @return this
     */
    public TemplateResource expand(String key, String expansion) {
        expansions.put(key, expansion);
        return this;
    }

    public InputStream toInputStream() { return new ByteArrayInputStream(this.getData().getBytes()); }
    public String toOutputString() { return this.getData(); }
    public String getData() {
        String expanded = template;
        for(String key : expansions.keySet())
            expanded = expanded.replaceAll("\\$"+key, expansions.get(key));
        return expanded;
    }
}
