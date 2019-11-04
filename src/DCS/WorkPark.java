package DCS;

import HB.HeartbeatLinstener;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * 其他程序操作ParkServer的媒介
 */
public class WorkPark extends UnicastRemoteObject implements ParkLocal{
    NodeSet nodeSet;
    public WorkPark(NodeSet nodeSet)throws RemoteException{
        this.nodeSet = nodeSet;
    }


    @Override
    public ObjectBean create(String domain){
        String node = UUID.randomUUID().toString();
        return create(domain,node,null,false);
    }

    @Override
    public ObjectBean create(String domain, String node,Serializable obj){
        return create(domain,node,obj,false);
    };

    @Override
    public ObjectBean create(String domain,String node,Serializable obj,boolean heartbeat){
        String dm = "_worker_"+domain;
        ObjectBean ob = new ObjectBean(dm, node,obj);
        nodeSet.add(dm,node,ob);
        if(heartbeat){
            //TODO 开启一个线程监听心跳，超时修改对应节点的存活状态
            long lastTime = ((Service)obj).getCreateTime();
            ob.setLastTime(lastTime);
            ob.setNextTime(lastTime+ConfigContext.getHeartBeatInterval());
            new Thread(new HeartbeatLinstener(nodeSet,ob)).start();
        }
        System.out.println("create a new node" + ob);
        return ob;
    }

    @Override
    public List<ObjectBean> get(String domain){
        String dm = "_worker_" + domain;
        List<ObjectBean> obs = new ArrayList<>();
        Map map = nodeSet.get(dm);
        for(Object object:map.values()){
            ObjectBean ob = (ObjectBean)object;
            if(ob.isAlive()){
                obs.add(ob);
            }
        }
        System.out.println("get domaim nodes in " + domain + ",size is " + (obs==null?0:obs.size()));
        return obs;
    }

    @Override
    public ObjectBean get(String domain, String node) {
        ObjectBean objectBean = null;
        String dm = "_worker_" + domain;
        objectBean = nodeSet.get(dm,node);
        System.out.println("get a node " + objectBean);
        return objectBean;
    }

    ;

}
