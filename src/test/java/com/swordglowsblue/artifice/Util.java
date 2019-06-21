package com.swordglowsblue.artifice;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Util {
    static JsonObject readFile(String path) throws IOException {
        return JsonHelper.deserialize(Files.readString(Paths.get("./src/test/resources/"+path)));
    }

    static JsonObject getResource(ArtificeResourcePack from, String path) throws IOException {
        return JsonHelper.deserialize(new InputStreamReader(from.open(from.getType(), new Identifier(path))));
    }

    static JsonObject getRootResource(ArtificeResourcePack from, String fname) throws IOException {
        return JsonHelper.deserialize(new InputStreamReader(from.openRoot(fname)));
    }
}
