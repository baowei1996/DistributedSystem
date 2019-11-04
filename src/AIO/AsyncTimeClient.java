package AIO;

import DCS.WareHouse;

public class AsyncTimeClient {
    //TODO 考虑使用线程池来改善操作
    public static Object asyncOperate(String host, int port, WareHouse in){
        AsyncTimeClientHandler ach = new AsyncTimeClientHandler(host, port,in);
        Thread t = new Thread(ach);
        try {
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ach.getResponse();
    }
}
