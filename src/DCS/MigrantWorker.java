package DCS;

import AIO.AsyncTimeServerHandler;
import HB.HeartbeatEntity;
import HB.HeartbeatSender;

import java.rmi.RemoteException;
import java.util.*;

/**
 * 工人类
 */
public class MigrantWorker{
    String host,workerType;
    int port;
    ObjectBean myNode;
    private int selfIndex=-1;
    private volatile boolean _interrupted;

    public MigrantWorker(){}
    public MigrantWorker(String host,int port,String workerType){
        init(host,port,workerType);
    }
    public void init(String host,int port,String workerType){
        this.host = host;
        this.port = port;
        this.workerType = workerType;
    }
    /**
     * 需要被动态代理的方法
     * @param inhouse
     * @return
     */
    public WareHouse doTask(WareHouse inhouse){
        return null;
    }

    public void waitWorking(String workerType){
        waitWorking(host,port,workerType);
    }

    public void waitWorking(String host, int port, String workerType){
        //开启工人服务器的监听
        AsyncTimeServerHandler listener = new AsyncTimeServerHandler(port);
        listener.setHandler(new Handler(this));
        new Thread(listener).start();

        doRegister(host,port,workerType);
    }

    public Workman[] getWorkerAll(){
        ParkLocal parkLocal = BeanContext.getParkLocal("work");
        List<ObjectBean> objectBeanList = null;
        try {
            objectBeanList = parkLocal.get(workerType);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        List<Workman> wms = new ArrayList<>();
        for(ObjectBean ob:objectBeanList){
            Service service = (Service) ob.getObject();
            wms.add(new Workman(service.getHost(),service.getPort()));
        }
        return wms.toArray(new Workman[wms.size()]);
    }

    public Workman[] getWorkerElse(){
        return null;
    }

    public Workman[] getWorkerElse(String workerType){
        ParkLocal parkLocal = BeanContext.getParkLocal("work");
        List<ObjectBean> objectBeanList = null;
        try {
            objectBeanList = parkLocal.get("_worker_"+workerType);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        List<Workman> wms = new ArrayList<>();
        for(ObjectBean ob:objectBeanList){
            Service service = (Service) ob.getObject();
            if(!(host.equals(service.getHost())&&(service.getPort()== port))){
                wms.add(new Workman(service.getHost(),service.getPort()));
            }
        }
        return wms.toArray(new Workman[wms.size()]);
    }

    public Workman getWorkerIndex(int index){
        return null;
    }

    public Workman getWorkerIndex(String workerType, int index){
        return null;
    }

    public int getSelfIndex(){
        return -1;
    }

    /**
     * 需要被重载的方法
     * @param inhouse
     * @return
     */
    public boolean receive(WareHouse inhouse){
        return false;
    }

    private void doRegister(String host,int port,String workerType){
        init(host,port,workerType);
        ParkLocal parkLocal = BeanContext.getParkLocal("work");
        try {
            myNode = parkLocal.create("_worker_" + workerType,String.valueOf(UUID.randomUUID()),new Service(host,port,System.currentTimeMillis()),true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        //TODO 开启一个线程来发送心跳包
        new Thread(new HeartbeatSender(myNode)).start();
    }

}
