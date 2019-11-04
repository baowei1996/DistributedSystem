package DCS;

import AIO.AsyncTimeClientHandler;

/**
 * 工人之间相互交流的媒介
 */
public class Workman {
    private String host;
    private int port;
    public Workman(){}
    public Workman(String host, int port){
        this.host = host;
        this.port = port;
    }
    public boolean receive(WareHouse inhouse){
        //封装传输的内容
        WareHouse in = new WareHouse();
        in.wrap("receive",new Class[]{WareHouse.class},new Object[]{inhouse});

        AsyncTimeClientHandler ach = new AsyncTimeClientHandler(host, port,in);
        Thread t = new Thread(ach);
        try {
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public String getHost(){
        return host;
    }
    public int getPort(){
        return port;
    }
}
