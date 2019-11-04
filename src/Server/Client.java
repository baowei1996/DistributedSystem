package Server;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1",8008);
        OutputStream outToServer = client.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);
        InputStream inFromServer = client.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        String buffer = null;
        out.writeUTF("Hello from " + client.getLocalSocketAddress()+"\n");
        for(int i=1;i<10;i++){
            out.writeUTF(client.getLocalSocketAddress() + " " + i + " times\n");
        }
        out.writeUTF("EOF");
        while (!"EOF".equals(buffer = in.readUTF())){
            System.out.println("Server says " + buffer);
        }
        client.close();
    }
}
