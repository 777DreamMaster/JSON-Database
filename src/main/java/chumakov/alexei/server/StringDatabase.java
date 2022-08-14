package chumakov.alexei.server;

import chumakov.alexei.Utils.JsonConverter;
import chumakov.alexei.Utils.JsonOperations;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StringDatabase implements Database<JsonElement> {

    private final Map<String, String> cells;
//    private static final String FILENAME_ENVIRONMENT_LOCAL = System.getProperty("user.dir") + "/JSON Database/task/src/server/data/db.json";
    private static final String FILENAME_ENVIRONMENT_LOCAL = System.getProperty("user.dir") + "/src/main/java/chumakov/alexei/server/data/db.json";
    private static final File DATABASE = new File(FILENAME_ENVIRONMENT_LOCAL);
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Lock readLock = lock.readLock();
    private static final Lock writeLock = lock.writeLock();

    public StringDatabase() {
        cells = loadDatabaseFromFile();
    }

    public String get(JsonElement key) {
        loadDatabaseFromFile();
        List<String> list = List.of(key.toString().replaceAll("[\\[\\]\"]", "").split(","));
        JsonObject jsonObject = JsonParser.parseString(cells.get(list.get(0))).getAsJsonObject();
        System.out.println(2);
        return JsonOperations.getValue(jsonObject, list.subList(1,list.size()));
    }

    public boolean set(JsonElement key, JsonElement value) {
        if (key.isJsonPrimitive()) {
            cells.put(key.getAsString(), value.toString());
        } else {
            List<String> list = List.of(key.toString().replaceAll("[\\[\\]\"]", "").split(","));
            JsonObject jsonObject = JsonParser.parseString(cells.get(list.get(0))).getAsJsonObject();
            JsonOperations.setValue(jsonObject, list.subList(1, list.size()), value);
            cells.put(list.get(0), jsonObject.toString());
        }
        writeDatabaseToFile();
        return true;
    }

    public boolean delete(JsonElement key) {
        if (key.isJsonPrimitive()) {
            if (cells.containsKey(key.getAsString())) {
                cells.remove(key.getAsString());
                writeDatabaseToFile();
                return true;
            }
        } else {
            List<String> list = List.of(key.toString().replaceAll("[\\[\\]\"]", "").split(","));
            JsonObject jsonObject = JsonParser.parseString(cells.get(list.get(0))).getAsJsonObject();
            boolean isDeleted = JsonOperations.deleteValue(jsonObject, list.subList(1, list.size()));
            if (isDeleted) {
                cells.put(list.get(0), jsonObject.toString());
                writeDatabaseToFile();
                return true;
            }
        }
        return false;
    }

    public void writeDatabaseToFile() {
        String data = JsonConverter.toJSON(cells);
        writeLock.lock();
        try (FileWriter fileWriter = new FileWriter(DATABASE)) {
            fileWriter.write(data);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        writeLock.unlock();
    }

    public Map<String, String> loadDatabaseFromFile() {
        Map<String, String> cells;
        readLock.lock();
        try {
            String data = new String(Files.readAllBytes(Path.of(FILENAME_ENVIRONMENT_LOCAL)));
            if (!data.equals("")) {
                cells = JsonConverter.getFromJSON(data, new TypeToken<HashMap<String, String>>(){}.getType());
            } else {
                cells = new HashMap<>(1000);
            }
        } catch (IOException e) {
            cells = new HashMap<>(1000);
        }
        readLock.unlock();
        return cells;
    }
}
