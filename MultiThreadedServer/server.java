import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;
class server{

    public Consumer<Socket> getConsumer(){
        return (clientSocket) -> {
            try{
                PrintWriter toClient =new PrintWriter(clientSocket.getOutputStream(), true);
                toClient.println("Hello from server");
                toClient.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
    public static void main(String[] args) {
        server server1 = new server();
        int port=8010;
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server started on port " + port);
            while(true){
               try{
                Socket  acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(()->{
                    server1.getConsumer().accept(acceptedSocket);
                });
                thread.start();
               }
               catch(IOException e){
                 e.printStackTrace();

               }
            }
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}