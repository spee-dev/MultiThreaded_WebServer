import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ExecutorService threadPool;

    public Server(int poolSize) {
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public void handleClient(Socket clientSocket) {
        try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) { // autoFlush = true
            toSocket.println("Hello from server " + clientSocket.getInetAddress());
            System.out.println("Handled client: " + clientSocket.getRemoteSocketAddress());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = 8010;
        int poolSize = 10;
        Server server = new Server(poolSize);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(0); // no timeout or adjust as needed
            System.out.println("Server is running on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                server.threadPool.execute(() -> server.handleClient(clientSocket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.threadPool.shutdown();
            System.out.println("Server shutdown.");
        }
    }
}
