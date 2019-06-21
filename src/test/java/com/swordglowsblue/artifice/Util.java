package com.swordglowsblue.artifice;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Util {
    static JsonObject readFile(String path) throws IOException {
        return readFile(Paths.get("./src/test/resources/"+path));
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
        for(Path path : Files.walk(dir).collect(Collectors.toList())) {
            if(!Files.isRegularFile(path)) return;
            assertEquals(Util.readFile(path), Util.readFile(Paths.get(path.toString().replace(ref, dump))));
        }
    }
}
