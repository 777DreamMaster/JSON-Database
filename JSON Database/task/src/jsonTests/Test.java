package jsonTests;

import Utils.JsonConverter;
import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        Path path = Path.of(System.getProperty("user.dir") + "/JSON Database/task/src/jsonTests/1.json");
        String line = new String(Files.readAllBytes(path));
//        System.out.println(line);

        JsonElement jsonElement = JsonParser.parseString(line);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonElement jsonElement2 = JsonParser.parseString(line);
        JsonObject jsonObject2 = jsonElement2.getAsJsonObject();

//        System.out.println(jsonObject.get("value"));
//        System.out.println(jsonObject.get("value").getAsJsonObject().get("name").getClass());

//        JsonObject newo = jsonObject.get("value").getAsJsonObject();
//        newo.addProperty("max", "s");
//        System.out.println(jsonObject.get("value").getAsJsonObject());
        List<String> list = List.of("person", "rocket");
        List<String> list2 = List.of("person","rocket", "launches");
        List<String> list3 = List.of("person","rocket","aeg");
        List<String> list4 = List.of("person","car");
        List<String> list5 = List.of();
        List<String> list6 = List.of("2");
        System.out.println(list6.subList(1, list6.size()));
//        List<String> list51 = List.of(jsonObject.get("key").toString().replaceAll("[\\[\\]\"]", "").split(","));
//        System.out.println(list51.get(1));
//        System.out.println(jsonObject.get("key").getAsString().getClass());
//        System.out.println(getValue(jsonObject, list5));
//        setValue(jsonObject, list3, JsonParser.parseString("25"));
//        setValue(jsonObject, list3, jsonObject2);
//        System.out.println(JsonConverter.toJSON(jsonObject));
//        deleteValue(jsonObject, list4);
//        System.out.println(jsonObject);
//        System.out.println(jsonObject.get("person").getAsJsonObject().keySet());
    }

    public static String getValue(JsonObject json, List<String> keys){
        JsonObject insideJson = json;
        JsonElement jsonElement = null;
        for (String key: keys) {
            if (insideJson.get(key) instanceof JsonPrimitive) {
                jsonElement = insideJson.get(key);
            } else {
                insideJson = insideJson.get(key).getAsJsonObject();
                jsonElement = insideJson;
            }
        }
        if (jsonElement == null) jsonElement = insideJson;
        return JsonConverter.toJSON(jsonElement);
    }

    public static void setValue(JsonObject json, List<String> keys, JsonElement value) {
        JsonObject insideJson = json;
        for (int i = 0; i < keys.size() - 1; i++) {
                insideJson = insideJson.get(keys.get(i)).getAsJsonObject();
        }
        if (value instanceof JsonPrimitive) {
            insideJson.addProperty(keys.get(keys.size() - 1), value.toString());
        } else {
            insideJson.add(keys.get(keys.size() - 1), value);
        }
    }

    public static void deleteValue(JsonObject json, List<String> keys) {
        JsonObject insideJson = json;
        for (int i = 0; i < keys.size() - 1; i++) {
            insideJson = insideJson.get(keys.get(i)).getAsJsonObject();
        }
        insideJson.remove(keys.get(keys.size() - 1));
    }
}
