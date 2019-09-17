package artificemc.artifice.api.core.resource;

import artificemc.artifice.api.core.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileResource implements Resource<File> {
    private final Path path;
    public FileResource(Path path) { this.path = path; }

    public File getData() { return path.toFile(); }

    public String toOutputString() {
        try { return FileUtils.readString(path); }
        catch(IOException e) { throw new RuntimeException(e); }
    }

    public InputStream toInputStream() {
        try { return Files.newInputStream(path); }
        catch(IOException e) { throw new RuntimeException(e); }
    }
}
