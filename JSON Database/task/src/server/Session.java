package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Session extends Thread {
    private final ServerSocket server;
    private final Socket socket;

    public Session(ServerSocket server, Socket socketForClient) {
        this.server = server;
        this.socket = socketForClient;
    }

    public void run() {
        try (
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            String line = input.readUTF();
            JsonObject request = JsonParser.parseString(line).getAsJsonObject();

            if (request.get("type").getAsString().equals("exit")) {
                output.writeUTF(Controller.getJsonResponse(true));
                server.close();
                System.exit(0);
            }
            String response = Controller.process(request);
            output.writeUTF(response);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
