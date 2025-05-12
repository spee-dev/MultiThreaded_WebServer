import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 8010;
        int numberOfClients = 10; // how many clients you want to simulate

        for (int i = 0; i < numberOfClients; i++) {
            int clientId = i + 1;
            Thread clientThread = new Thread(() -> {
                try (Socket socket = new Socket(serverAddress, port);
                     PrintWriter toServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                     BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    // Send message
                    toServer.println("Hello from client #" + clientId);

                    // Read server response
                    String response = fromServer.readLine();
                    System.out.println("Client #" + clientId + " received: " + response);

                } catch (Exception e) {
                    System.out.println("Client #" + clientId + " failed: " + e.getMessage());
                }
            });

            clientThread.start();
        }
    }
}
