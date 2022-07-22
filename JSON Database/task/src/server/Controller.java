package server;

import Utils.JsonConverter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.*;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {

    private static final int PORT = 23456;
    private static final String ADDRESS = "127.0.0.1";
    private static final int THREADS = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executor = Executors.newFixedThreadPool(THREADS);
    private static final StringDatabase database = new StringDatabase();


    public static String process(JsonObject request) {
        switch (request.get("type").getAsString()) {
            case "get":
                return getJsonResponse(database.get(request.get("key")));
            case "set":
                return getJsonResponse(database.set(request.get("key"), request.get("value")));
            case "delete":
                return getJsonResponse(database.delete(request.get("key")));
            case "exit":
                return getJsonResponse(true);
            default:
                return getJsonResponse(false);
        }
    }

    public static void connect() {
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            System.out.println("Server started!");
            while (true) {
                Session session = new Session(server, server.accept());
                executor.submit(session);
            }
        } catch (IOException e) {
            System.out.println("Server error");
        }
    }

    static String getJsonResponse(String text) {
        JsonObject json = new JsonObject();
        json.addProperty("response", Objects.equals(null, text) ? "ERROR" : "OK");
        if (json.get("response").getAsString().equals("ERROR")) {
            json.addProperty("reason", "No such key");
        } else {
            json.add("value", JsonParser.parseString(text));
        }
        return JsonConverter.toJSON(json);
    }

    static String getJsonResponse(boolean f) {
        JsonObject json = new JsonObject();
        json.addProperty("response", f ? "OK" : "ERROR");
        if (json.get("response").getAsString().equals("ERROR")) {
            json.addProperty("reason", "No such key");
        }
        return json.toString();
    }
}
