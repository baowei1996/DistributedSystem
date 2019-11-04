package DCS;

import java.io.Serializable;
import java.util.Objects;

/**
 * 职介者保存的工人节点代表类
 */
public class ObjectBean implements Serializable {
    private Serializable obj;
    private String node;
    private String domain;
    private boolean alive;
    private long lastTime;
    private long nextTime;

    public ObjectBean(String domain, String node, Serializable obj) {
        this.domain = domain;
        this.node = node;
        this.obj = obj;
        this.alive = true;
    }

    public String getNode() {
        return node;
    }

    public String getDomain() {
        return domain;
    }

    public Serializable getObject(){
        return obj;
    }

    public boolean isAlive(){
        return this.alive;
    }

    public synchronized void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public synchronized void setNextTime(long nextTime) {
        this.nextTime = nextTime;
    }

    public long getLastTime() {
        return lastTime;
    }

    public long getNextTime() {
        return nextTime;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setObject(Serializable obj){
        this.obj = obj;
    }

    public synchronized void setAlive(boolean alive){
        this.alive = alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectBean that = (ObjectBean) o;
        return Objects.equals(node, that.node) &&
                Objects.equals(domain, that.domain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, domain);
    }

    @Override
    public String toString() {
        return "ObjectBean{" +
                ", node='" + node + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}
