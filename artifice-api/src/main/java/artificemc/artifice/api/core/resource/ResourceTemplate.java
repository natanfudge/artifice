package artificemc.artifice.api.core.resource;

import artificemc.artifice.impl.core.util.IdUtils;

import java.util.function.BiConsumer;

public class ResourceTemplate {
    private final String namespace;
    private final String pathTemplate;
    private final String bodyTemplate;

    public ResourceTemplate(String namespace, String pathTemplate, String bodyTemplate) {
        IdUtils.validateIdentifier(namespace, "validate");
        this.namespace = namespace;
        this.pathTemplate = pathTemplate;
        this.bodyTemplate = bodyTemplate;
    }

    private void validateKey(String key) {
        if(!key.matches("^[a-zA-Z0-9_]+$"))
            throw new IllegalArgumentException("Invalid key '"+key+"', should match ^[a-zA-Z0-9_]+$");
    }

    public ResourceTemplate expand(String key, String expandTo) { return expand(key, expandTo, expandTo); }
    public ResourceTemplate expand(String key, String pathTo, String expandTo) {
        this.validateKey(key);
        IdUtils.validateIdentifier("validate", pathTo);
        return new ResourceTemplate(namespace,
            pathTemplate.replaceAll("\\$\\{"+key+"}", pathTo),
            bodyTemplate.replaceAll("\\$\\{"+key+"}", expandTo));
    }

    public ResourceTemplate expandPath(String key, String expandTo) {
        this.validateKey(key);
        IdUtils.validateIdentifier("validate", expandTo);
        return new ResourceTemplate(namespace, pathTemplate.replaceAll("\\$\\{"+key+"}", expandTo), bodyTemplate);
    }

    public ResourceTemplate expandBody(String key, String expandTo) {
        this.validateKey(key);
        return new ResourceTemplate(namespace, pathTemplate, bodyTemplate.replaceAll("\\$\\{"+key+"}", expandTo));
    }

    public String getPath() { return IdUtils.of(namespace, pathTemplate); }
    public StringResource toResource() { return new StringResource(this.bodyTemplate); }

    public void register(BiConsumer<String, StringResource> registrar) {
        registrar.accept(this.getPath(), this.toResource());
    }
}
