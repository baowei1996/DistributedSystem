package HB;

import DCS.ObjectBean;

import java.io.Serializable;

public class HeartbeatEntity implements Serializable {
    private long time;
    private ObjectBean node;

    public void setNode(ObjectBean node){
        this.node = node;
    }

    public ObjectBean getNode(){
        return node;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
