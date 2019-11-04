package DCS;

import HB.HeartbeatHandler;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * 负责统筹包工人，工人，职介者之间的关系
 */
public class BeanContext {
    public static ParkLocal getParkLocal(String serviceName){
        //读取配置，获取相应的IP和端口
        ParkLocal parkLocal = null;
        try {
            String remoteAddr = "rmi://"+ConfigContext.getParkAddress()+"/"+serviceName;
            parkLocal = (ParkLocal) Naming.lookup(remoteAddr);
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
        return parkLocal;
    }
    public static HeartbeatHandler getHeartbeatHandler(){
        HeartbeatHandler heartbeatHandler = null;
        try {
            String remoteAddr = "rmi://"+ConfigContext.getParkAddress()+"/heartbeat";
            heartbeatHandler = (HeartbeatHandler) Naming.lookup(remoteAddr);
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
        return heartbeatHandler;
    }
}
