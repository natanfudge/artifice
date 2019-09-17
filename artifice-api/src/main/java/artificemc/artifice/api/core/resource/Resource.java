package artificemc.artifice.api.core.resource;

import java.io.InputStream;

public interface Resource<T> {
    T getData();
    String toOutputString();
    InputStream toInputStream();

    enum Type {
        CLIENT("assets"), SERVER("data");
        public final String pathPrefix;
        Type(String pathPrefix) { this.pathPrefix = pathPrefix; }
    }
}
