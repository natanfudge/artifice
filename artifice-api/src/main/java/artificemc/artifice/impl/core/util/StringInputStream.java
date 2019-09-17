package artificemc.artifice.impl.core.util;

import java.io.ByteArrayInputStream;

public class StringInputStream extends ByteArrayInputStream {
    public StringInputStream(String from) { super(from.getBytes()); }

    public static StringInputStream from(Object from) {
        return new StringInputStream(from.toString());
    }
}
