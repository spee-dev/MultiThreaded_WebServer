import java.io.*;
import java.net.*;

class Server {

    public void run() throws Exception {
        int port = 8010;
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000); // 10 seconds timeout

        System.out.println("Server is running on port: " + port);

        while (true) {
            try {
                Socket acceptedConnection = serverSocket.accept();
                System.out.println("Client connected: " + acceptedConnection.getRemoteSocketAddress());

                PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));

                toClient.println("Hello from the server");

                
                String clientMessage = fromClient.readLine();
                System.out.println("Client says: " + clientMessage);

                fromClient.close();
                toClient.close();
                acceptedConnection.close();

            } catch (SocketTimeoutException e) {
                System.out.println("No client connected within timeout.");
                break; 
            }
        }

        serverSocket.close();
        System.out.println("Server stopped.");
    }

    public static void main(String[] args) {
        try {
            new Server().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
