import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

class client {
    public Runnable geRunnable(){
    return new Runnable() {
        @Override
        public void run()  {
            int port=8010;
           try {
            InetAddress address=InetAddress.getByName("localhost");
            Socket socket=new Socket(address,port);
            PrintWriter toSocket=new PrintWriter(socket.getOutputStream());
            BufferedReader fromSocket=new BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
            toSocket.println("Hello from the client");
            String line=fromSocket.readLine();
            System.out.println("Server response: " + line);
            toSocket.close();
            fromSocket.close();
            socket.close();
           } catch (Exception e) {
           e.printStackTrace();
           }
        }
    };
}
     public static void main(String[] args) {
        client client1 = new client();
        for(int i=0;i<100;i++){
            try {
                Thread thread =new Thread(client1.geRunnable());
                thread.start();
            } catch (Exception e) {
                return;  
            }
        }
     }
 }
