package DCS;

import HB.HeartbeatHandler;
import HB.HeartbeatHandlerImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.*;

/**
 * 职介者
 * 负责工人的注册和心跳检测
 * 向包工头提供可用的工人
 */
public class ParkServer {
    NodeSet nodeSet = new NodeSet();

    public void service(String host,int port){
        //开启工人服务器的监听
        try {
            LocateRegistry.createRegistry(port);
            //注册工人服务
            ParkLocal parkLocal = new WorkPark(nodeSet);
            String serviceName = "rmi://"+host+":"+port + "/work";
            Naming.bind(serviceName,parkLocal);
            //注册心跳服务
            HeartbeatHandler heartbeatHandler = new HeartbeatHandlerImpl(nodeSet);
            serviceName = "rmi://"+host+":"+port + "/heartbeat";
            Naming.bind(serviceName,heartbeatHandler);
        } catch (RemoteException | AlreadyBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ParkServer parkServer = new ParkServer();
        parkServer.service("127.0.0.1",8010);
    }
}
