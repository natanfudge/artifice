package com.swordglowsblue.artifice.test;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Util {
    static final String ROOT = "./src/test/resources/com/swordglowsblue/artifice/test/";

    static JsonObject readFile(String path) throws IOException {
        return readFile(Paths.get(ROOT+path));
    }

    static JsonObject readFile(Path path) throws IOException {
        return JsonHelper.deserialize(Files.readString(path));
    }

    static JsonObject getResource(ArtificeResourcePack from, String path) throws IOException {
        return JsonHelper.deserialize(new InputStreamReader(from.open(from.getType(), new Identifier(path))));
    }

    static JsonObject getRootResource(ArtificeResourcePack from, String fname) throws IOException {
        return JsonHelper.deserialize(new InputStreamReader(from.openRoot(fname)));
    }

    static void compareDirectoryToDump(Path dir, String ref, String dump) throws IOException {
        Files.walk(dir).forEach((IOConsumer<Path>)path -> {
            if(!Files.isRegularFile(path)) return;
            assertEquals(Util.readFile(path), Util.readFile(Paths.get(path.toString().replace(ref, dump))));
        });
        cleanDirectory(Paths.get(dir.toString().replace(ref, dump)));
    }

    static boolean cleanDirectory(Path dir) throws IOException { return cleanDirectory(dir.toFile()); }
    static boolean cleanDirectory(File dir) throws IOException {
        File[] contents = dir.listFiles();
        if(contents != null) for(File file : contents) cleanDirectory(file);
        return dir.delete();
    }

    @FunctionalInterface
    interface IOConsumer<T> extends Consumer<T> {
        void acceptThrows(T t) throws IOException;
        default void accept(T t) {
            try { acceptThrows(t); } catch(IOException e) { throw new RuntimeException(e); }
        }
    }
}
