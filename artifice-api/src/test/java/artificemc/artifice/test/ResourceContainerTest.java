package artificemc.artifice.test;

import artificemc.artifice.api.builders.JsonObjectBuilder;
import artificemc.artifice.api.core.resource.*;
import artificemc.artifice.api.core.resource.container.ResourceContainer;
import artificemc.artifice.api.core.resource.container.ResourceNotFoundException;
import artificemc.artifice.api.core.util.FileUtils;
import artificemc.artifice.api.core.util.FileUtils.IOConsumer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static artificemc.artifice.api.core.util.CollectionUtils.setOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceContainerTest {
    @Test void testMeta() throws IOException {
        assertEquals(container.getId(), "artifice:test");
        assertEquals(container.getType(), Resource.Type.CLIENT);
        assertEquals(container.getNamespaces(), setOf("artifice", "test"));

        assertEquals(container.getMetadata().getData(), "test1\ntest2");
        assertEquals(container.getMetadata().toOutputString(), "test1\ntest2");
        assertEquals(FileUtils.readInput(container.getMetadata().toInputStream()), "test1\ntest2");
    }

    @Test void testResources() throws IOException {
        assertEquals(container.getResources().keySet(), setOf(
            "artifice:test.txt",
            "artifice:test.json",
            "artifice:template_test_red.txt",
            "artifice:template_test_green.txt",
            "artifice:template_test_blue.txt"
        ));

        Resource testTxt = container.getResource("artifice:test.txt");
        assertEquals(testTxt.getData(), "test1\ntest2");
        assertEquals(testTxt.toOutputString(), "test1\ntest2");
        assertEquals(FileUtils.readInput(testTxt.toInputStream()), "test1\ntest2");

        Resource testJson = container.getResource("artifice:test.json");
        assertEquals(testJson.getData(), new JsonObjectBuilder().set("test", "test").build());
        assertEquals(testJson.toOutputString(), "{\n  \"test\": \"test\"\n}");
        assertEquals(FileUtils.readInput(testJson.toInputStream()), "{\"test\":\"test\"}");

        Resource redTxt = container.getResource("artifice:template_test_red.txt");
        assertEquals(redTxt.getData(), "I drive a red Ford.");
        assertEquals(redTxt.toOutputString(), "I drive a red Ford.");
        assertEquals(FileUtils.readInput(redTxt.toInputStream()), "I drive a red Ford.");

        Resource greenTxt = container.getResource("artifice:template_test_green.txt");
        assertEquals(greenTxt.getData(), "I drive a green Subaru.");
        assertEquals(greenTxt.toOutputString(), "I drive a green Subaru.");
        assertEquals(FileUtils.readInput(greenTxt.toInputStream()), "I drive a green Subaru.");

        Resource blueTxt = container.getResource("artifice:template_test_blue.txt");
        assertEquals(blueTxt.getData(), "I drive a blue Hyundai.");
        assertEquals(blueTxt.toOutputString(), "I drive a blue Hyundai.");
        assertEquals(FileUtils.readInput(blueTxt.toInputStream()), "I drive a blue Hyundai.");
    }

    @Test void testDump() throws IOException {
        container.dump("../../../artifice-api/src/test/resources/dump");
        Files.walk(Paths.get("../../../artifice-api/src/test/resources/dump_ref")).forEach((IOConsumer<Path>) path -> {
            if(!Files.isRegularFile(path)) return;
            assertEquals(
                FileUtils.readString(path).trim(),
                FileUtils.readString(Paths.get(path.toString().replace("_ref", ""))).trim()
            );
        });
        cleanDirectory(Paths.get("../../../artifice-api/src/test/resources/dump").toFile());
    }

    private static ResourceContainer container = new ResourceContainer() {
        public String getId() { return "artifice:test"; }
        public Resource.Type getType() { return Resource.Type.CLIENT; }
        public Resource getMetadata() { return new StringResource("test1","test2"); }
        public Set<String> getNamespaces() { return setOf("artifice", "test"); }

        private Map<String, Resource> resources = null;
        private void createResources() {
            resources = new HashMap<>();
            resources.put("artifice:test.txt", new StringResource("test1","test2"));
            resources.put("artifice:test.json", new JsonResource(new JsonObjectBuilder().set("test", "test").build()));

            ResourceTemplate template = new ResourceTemplate("artifice", "template_test_${color}.txt", "I drive a ${color} ${car}." );
            for(int i = 0; i < 3; i++) {
                template.expand("color", "red,green,blue".split(",")[i])
                    .expandBody("car", "Ford,Subaru,Hyundai".split(",")[i])
                    .register(resources::put);
            }
        }

        public Resource getResource(String id) throws ResourceNotFoundException {
            if(resources == null) createResources();
            if(resources.containsKey(id)) return resources.get(id);
            throw new ResourceNotFoundException("Resource "+id+" not found");
        }

        public Map<String, Resource> getResources() {
            if(resources == null) createResources();
            return resources;
        }
    };

    private static boolean cleanDirectory(File dir) {
        File[] contents = dir.listFiles();
        if(contents != null) for(File file : contents) cleanDirectory(file);
        return dir.delete();
    }
}
