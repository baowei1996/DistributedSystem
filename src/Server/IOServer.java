package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class IOServer {
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(8008);
        while (true){
            Socket server = serverSocket.accept();
            System.out.println("Hello ,I can speak now.");
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            DataInputStream in = new DataInputStream(server.getInputStream());
            String buffer = null;

            while(!"EOF".equals(buffer = in.readUTF())){
                System.out.println(buffer);
                out.writeUTF("echo: " + buffer + "\n");
            }
            out.writeUTF(buffer);
            server.close();
        }
    }
}
