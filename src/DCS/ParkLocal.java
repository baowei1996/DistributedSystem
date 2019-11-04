package DCS;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * ParkLocal的接口类，定义包工头、工人之间同职介者之间交流的接口
 */
public interface ParkLocal extends Remote {
    //创造节点
    ObjectBean create(String domain) throws RemoteException;
    ObjectBean create(String domain, String node, Serializable ob) throws RemoteException;
    ObjectBean create(String domain,String node,Serializable ob,boolean heartbeat) throws RemoteException;
    //获得节点
    List<ObjectBean> get(String domain) throws RemoteException;
    ObjectBean get(String domain,String node) throws RemoteException;
}
