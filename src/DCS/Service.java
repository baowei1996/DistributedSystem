package DCS;

import java.io.Serializable;

//用于保存服务信息的类
public class Service implements Serializable {
    String host;
    int port;
    long createTime;
    public Service(String host,int port,long createTime){
        this.host = host;
        this.port = port;
        this.createTime = createTime;
    }

    public String getHost(){
        return this.host;
    }
    public int getPort(){
        return this.port;
    }
    public long getCreateTime(){ return this.createTime; }
}
