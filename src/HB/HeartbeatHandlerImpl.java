package HB;

import DCS.ConfigContext;
import DCS.NodeSet;
import DCS.ObjectBean;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class HeartbeatHandlerImpl extends UnicastRemoteObject implements HeartbeatHandler {
    private NodeSet nodeSet;
    private long heartBeatInterval = ConfigContext.getHeartBeatInterval();
    public HeartbeatHandlerImpl(NodeSet nodeSet) throws RemoteException {
        this.nodeSet = nodeSet;
    }
    public boolean sendHeartBeat(HeartbeatEntity info) throws RemoteException{
        ObjectBean objectBean = nodeSet.get(info.getNode().getDomain(),info.getNode().getNode());
        long currentTime = info.getTime();

        if(currentTime < objectBean.getNextTime()){
            objectBean.setLastTime(currentTime);
            objectBean.setNextTime(currentTime+heartBeatInterval);
            objectBean.setAlive(true);
            nodeSet.add(objectBean.getDomain(),objectBean.getNode(),objectBean);
            return true;
        }else{
            objectBean.setAlive(false);
            return false;
        }
    }
}