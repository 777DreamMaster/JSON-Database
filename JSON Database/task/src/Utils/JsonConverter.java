package Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.lang.reflect.Type;

public class JsonConverter {

    private static final Gson gson = new GsonBuilder()
//                                        .setPrettyPrinting() // Commented for tests passing
                                        .create();

    public static <T> T getFromJSON(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static <T> String toJSON(T clazz) {
        return gson.toJson(clazz);
    }

}
