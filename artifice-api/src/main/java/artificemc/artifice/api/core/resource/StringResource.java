package artificemc.artifice.api.core.resource;

import artificemc.artifice.impl.core.util.StringInputStream;

import java.io.InputStream;

public class StringResource implements Resource<String> {
    private final String data;
    public StringResource(String... lines) { this.data = String.join("\n", lines); }

    public String getData() { return data; }
    public String toOutputString() { return data; }
    public InputStream toInputStream() { return new StringInputStream(data); }
}
