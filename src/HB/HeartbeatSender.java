package HB;

import DCS.BeanContext;
import DCS.ConfigContext;
import DCS.ObjectBean;

public class HeartbeatSender implements Runnable {
    private HeartbeatEntity entity;
    private boolean isRunning = true;
    //  最近的心跳时间
    private long lastHeartbeat;
    // 心跳间隔时间
    private long heartBeatInterval = 5 * 1000;

    public HeartbeatSender(ObjectBean node){
        entity = new HeartbeatEntity();
        entity.setNode(node);
    }

    public void run() {
        try {
            while (isRunning) {
                HeartbeatHandler handler = BeanContext.getHeartbeatHandler();
                long startTime = System.currentTimeMillis();
                // 是否达到发送心跳的周期时间
                if (startTime - lastHeartbeat > heartBeatInterval) {
                    System.out.println("send a heart beat");
                    lastHeartbeat = startTime;

                    entity.setTime(startTime);

                    // 向服务器发送心跳，依据返回决定是否继续发送心跳
                    isRunning = handler.sendHeartBeat(entity);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
