package client;

import com.google.gson.JsonObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class Controller {

    private static final int PORT = 23456;
    private static final String ADDRESS = "127.0.0.1";

    private static final String PATH_TO_SCRIPTS_TEST = "src/client/data/";
    private static final String PATH_TO_SCRIPTS_LOCAL = System.getProperty("user.dir") + "/JSON Database/task/src/client/data/";

    public static void connect(Args request) {
        try (
                Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");

            String json = createJson(request);
            System.out.printf("Sent: %s%n", json);
            output.writeUTF(json);
            String receivedMsg = input.readUTF();
            System.out.printf("Received: %s", receivedMsg);
        } catch (IOException e) {
            System.out.println("Client error");
        }
    }

    private static String createJson(Args request) {
        if (request.getFileName() != null) {
            try {
                return new String(Files.readAllBytes(Path.of(PATH_TO_SCRIPTS_TEST + request.getFileName())));
            } catch (IOException e) {
                return "Client error1";
            }
        } else {
            JsonObject json = new JsonObject();
            json.addProperty("type", request.getRequest());
            if (!json.get("type").getAsString().equals("exit")) {
                json.addProperty("key", request.getKey());
            }
            if (json.get("type").getAsString().equals("set")) {
                json.addProperty("value", request.getValue());
            }
            return json.toString();
        }
    }
}

