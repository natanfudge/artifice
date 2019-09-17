package artificemc.artifice.api.core.resource.container;

import java.io.FileNotFoundException;

public class ResourceNotFoundException extends FileNotFoundException {
    public ResourceNotFoundException(String message) { super(message); }
}
