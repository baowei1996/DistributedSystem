package HB;


import DCS.ConfigContext;
import DCS.NodeSet;
import DCS.ObjectBean;

public class HeartbeatLinstener implements Runnable{
    private NodeSet nodeSet;
    private long heartBeatInterval = ConfigContext.getHeartBeatInterval();
    private ObjectBean objectBean;
    private boolean isRunning = true;

    public HeartbeatLinstener(NodeSet nodeSet ,ObjectBean objectBean){
        this.nodeSet = nodeSet;
        this.objectBean = objectBean;
    }

    public void run(){
        long lastTime = System.currentTimeMillis();
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            // 是否达到发送心跳的周期时间
            if (startTime - lastTime > heartBeatInterval) {
                lastTime = startTime;
                if(objectBean.isAlive()){
                    objectBean.setAlive(false);
                }else {
                    nodeSet.delete(objectBean.getDomain(),objectBean.getNode());
                    isRunning = false;
                }
            }
        }
    }

}
