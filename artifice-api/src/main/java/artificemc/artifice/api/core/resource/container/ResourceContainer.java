package artificemc.artifice.api.core.resource.container;

import artificemc.artifice.api.core.resource.Resource;
import artificemc.artifice.impl.core.util.IdUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public interface ResourceContainer {
    String getId();
    Resource.Type getType();
    Set<String> getNamespaces();
    Resource getMetadata();
    Map<String, Resource> getResources();
    default String getMetadataFilename() { return "pack.mcmeta"; }

    default Resource getResource(String id) throws ResourceNotFoundException { return getResource(IdUtils.namespaceOf(id), IdUtils.pathOf(id)); }
    default Resource getResource(String namespace, String path) { return this.getResources().get(IdUtils.of(namespace, path)); }

    default void dump(String folderPath) throws IOException {
        File dir = new File(folderPath);
        if(!dir.exists() && !dir.mkdirs()) throw new IOException("Cannot create directory "+folderPath);
        if(!dir.isDirectory()) throw new IOException("Expected directory at "+folderPath);
        if(!dir.canWrite()) throw new IOException("Permission denied for directory "+folderPath);

        final BiFunction<String, Resource, IOException> writeResourceFile = (path, res) -> {
            File output = new File(path);
            if(!output.getParentFile().exists() && !output.getParentFile().mkdirs())
                return new IOException("Cannot create directory "+output.getParentFile().getPath());
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(output))) { writer.write(res.toOutputString()); }
            catch(IOException e) { return e; }
            return null;
        };

        IOException e = writeResourceFile.apply(folderPath+"/"+this.getMetadataFilename(), this.getMetadata());
        if(e != null) throw e;
        for(String id : this.getResources().keySet()) {
            String path = String.format("./%s/%s/%s/%s", folderPath, this.getType().pathPrefix, IdUtils.namespaceOf(id), IdUtils.pathOf(id));
            e = writeResourceFile.apply(path, this.getResource(id));
            if(e != null) throw e;
        }
    }
}
