package Utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public class JsonOperations {
    public static String getValue(JsonObject json, List<String> keys){
        JsonObject insideJson = json;
        JsonElement jsonElement = null;
        for (String key: keys) {
            if (insideJson.get(key) == null) {
                return null;
            }
            if (insideJson.get(key).isJsonPrimitive()) {
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
        if (value.isJsonPrimitive()) {
            insideJson.addProperty(keys.get(keys.size() - 1), value.getAsString());
        } else {
            insideJson.add(keys.get(keys.size() - 1), value);
        }
    }

    public static boolean deleteValue(JsonObject json, List<String> keys) {
        JsonObject insideJson = json;
        for (int i = 0; i < keys.size() - 1; i++) {
            insideJson = insideJson.get(keys.get(i)).getAsJsonObject();
        }
        if (insideJson.get(keys.get(keys.size() - 1)) != null) {
            insideJson.remove(keys.get(keys.size() - 1));
            return true;
        }
        return false;
    }
}
