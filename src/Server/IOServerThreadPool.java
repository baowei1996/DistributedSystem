package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOServerThreadPool {

    public static void main(String[] args) throws IOException{
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(8008);

        while (true){
            Socket server = serverSocket.accept();
            executorService.submit(()->{
                try{
                    System.out.println("Just connected to " + server.getRemoteSocketAddress());
                    DataOutputStream out = new DataOutputStream(server.getOutputStream());
                    DataInputStream in = new DataInputStream(server.getInputStream());
                    String buffer = null;

                    while(!"EOF".equals(buffer = in.readUTF())){
                        System.out.println(buffer);
                        out.writeUTF("echo: " + buffer + "\n");
                    }
                    out.writeUTF(buffer);
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            });
        }
    }
}
