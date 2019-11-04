package DCS;

import AIO.AsyncTimeClientHandler;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 包工头从职介者出获取的
 * 操作工人类的媒介
 */
public class WorkerLocal implements Serializable {
    MigrantWorker worker;
    String host;
    int port;

    public WorkerLocal(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public WareHouse doTask(WareHouse inhouse){
        //封装传输的内容
        WareHouse in = new WareHouse();
        in.wrap("doTask",new Class[]{WareHouse.class},new Object[]{inhouse});

        WareHouse outhouse = new WareHouse();
        outhouse.setReady(WareHouse.NOTREADY);
        AsyncTimeClientHandler ach = new AsyncTimeClientHandler(host, port,in);
        Thread t = new Thread(ach);
        try {
            t.start();
            t.join();
            if(ach.getResponse() != null){
                outhouse = (WareHouse)ach.getResponse();
                outhouse.setReady(WareHouse.READY);
            }else {
                outhouse = new WareHouse();
                outhouse.setReady(WareHouse.EXCEPTION);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            outhouse.setReady(WareHouse.EXCEPTION);
        }
        return outhouse;
    }

    //TODO 在时间超时之后，将停止进程
    public WareHouse doTask(WareHouse inhouse, long timeoutseconds){
        return null;
    }
    //TODO 调用本地的中断方法，注意这个方法要求在doTask方法中调用才有效，不然无效
    public void interrupt(){}
    public void setWorker(MigrantWorker migrantWorker){
        this.worker = migrantWorker;
    }
}
