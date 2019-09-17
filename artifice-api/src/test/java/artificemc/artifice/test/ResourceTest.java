package artificemc.artifice.test;

import artificemc.artifice.api.builders.JsonObjectBuilder;
import artificemc.artifice.api.core.resource.*;
import artificemc.artifice.api.core.util.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceTest {
    @Test void testStringResource() throws IOException {
        StringResource resource = new StringResource("test1", "test2");
        assertEquals(resource.getData(), "test1\ntest2");
        assertEquals(resource.toOutputString(), "test1\ntest2");

        String str = FileUtils.readInput(resource.toInputStream()).trim();
        assertEquals(str, "test1\ntest2");
    }

    @Test void testJsonResource() throws IOException {
        JsonResource resource = new JsonResource(new JsonObjectBuilder()
            .set("key1", "value1")
            .set("key2", 123)
            .build());
        assertEquals(resource.getData().toString(), "{\"key1\":\"value1\",\"key2\":123}");
        assertEquals(resource.toOutputString(), "{\n  \"key1\": \"value1\",\n  \"key2\": 123\n}");

        String str = FileUtils.readInput(resource.toInputStream()).trim();
        assertEquals(str, "{\"key1\":\"value1\",\"key2\":123}");
    }

    @Test void testFileResource() throws IOException {
        Path path = Paths.get("../../../artifice-api/src/test/resources/file_resource.json");
        FileResource resource = new FileResource(path);
        assertEquals(resource.getData(), path.toFile());
        assertEquals(resource.toOutputString(), Files.lines(path).collect(Collectors.joining("\n")));

        String str = FileUtils.readInput(resource.toInputStream()).trim();
        assertEquals(str, "{ \"test\": \"test\" }");
    }

    @ParameterizedTest
    @MethodSource("templateArgsProvider")
    void testResourceTemplate(String path, String name, String color, String brand, String model) throws IOException {
        String expected =
            "{" +
            "\n  \"person\": \""+name+"\"," +
            "\n  \"color\": \""+color+"\"," +
            "\n  \"brand\": \""+brand+"\"," +
            "\n  \"model\": \""+model+"\"" +
            "\n}";
        ResourceTemplate expanded = template
            .expand("name", name.toLowerCase(), name)
            .expandPath("brand", brand.toLowerCase())
            .expandBody("color", color)
            .expandBody("brand", brand)
            .expandBody("model", model);
        StringResource resource = expanded.toResource();

        assertEquals(expanded.getPath(), path);
        assertEquals(resource.getData(), expected);
        assertEquals(resource.toOutputString(), expected);
        String str = FileUtils.readInput(resource.toInputStream()).trim();
        assertEquals(str, expected);
    }

    @ParameterizedTest
    @MethodSource("templateArgsProvider")
    void testConvertToTemplate(String path, String name, String color, String brand, String model) throws IOException {
        String expected = "{\"person\":\""+name+"\",\"color\":\""+color+"\",\"brand\":\""+brand+"\",\"model\":\""+model+"\"}";
        ResourceTemplate expanded = converted
            .expand("name", name.toLowerCase(), name)
            .expandPath("brand", brand.toLowerCase())
            .expandBody("color", color)
            .expandBody("brand", brand)
            .expandBody("model", model);
        StringResource resource = expanded.toResource();

        assertEquals(expanded.getPath(), path);
        assertEquals(resource.getData(), expected);
        assertEquals(resource.toOutputString(), expected);
        String str = FileUtils.readInput(resource.toInputStream()).trim();
        assertEquals(str, expected);
    }

    private final ResourceTemplate template = new ResourceTemplate("artifice", "test_${name}_${brand}.json",
        "{" +
        "\n  \"person\": \"${name}\"," +
        "\n  \"color\": \"${color}\"," +
        "\n  \"brand\": \"${brand}\"," +
        "\n  \"model\": \"${model}\"" +
        "\n}"
    );

    private final ResourceTemplate converted = new JsonObjectBuilder()
        .set("person", "${name}")
        .set("color", "${color}")
        .set("brand", "${brand}")
        .set("model", "${model}")
        .toTemplate("artifice", "test_${name}_${brand}.json");

    static Stream<Arguments> templateArgsProvider() {
        return Stream.of(
            Arguments.of("artifice:test_john_ford.json", "John", "Red", "Ford", "Ranger"),
            Arguments.of("artifice:test_jane_mazda.json", "Jane", "Silver", "Mazda", "CX-3"),
            Arguments.of("artifice:test_james_hyundai.json", "James", "White", "Hyundai", "Sonata"),
            Arguments.of("artifice:test_jordan_subaru.json", "Jordan", "Blue", "Subaru", "Outback")
        );
    }
}
