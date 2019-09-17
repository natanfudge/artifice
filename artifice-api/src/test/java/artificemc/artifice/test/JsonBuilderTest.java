package artificemc.artifice.test;

import artificemc.artifice.api.builders.JsonArrayBuilder;
import artificemc.artifice.api.builders.JsonObjectBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonBuilderTest {
    @Test void testObjectBuilder() {
        JsonObject json = new JsonObjectBuilder()
            .set("number", 1.0f).set("string", "test")
            .set("char", 'x').set("bool", true)
            .set("obj", new JsonObjectBuilder().set("test", "test").build())
            .set("arr", new JsonArrayBuilder().add(783).build())
            .build();
        assertEquals(json.toString(),
            "{\"number\":1.0,\"string\":\"test\",\"char\":\"x\",\"bool\":true,\"obj\":{\"test\":\"test\"},\"arr\":[783]}");
    }

    @Test void testArrayBuilder() {
        JsonArray json = new JsonArrayBuilder()
            .add(1.0f).add("test").add('x').add(true)
            .add(new JsonObjectBuilder().set("test", "test").build())
            .add(new JsonArrayBuilder().add(783).build())
            .build();
        assertEquals(json.toString(),
            "[1.0,\"test\",\"x\",true,{\"test\":\"test\"},[783]]");
    }
}
