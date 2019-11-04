package AIO;

public class TestServer {
    public static void main(String args[]){
        new Thread(new AsyncTimeServerHandler(8010)).start();
    }
}
