package DCS;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * 输入输出的统一格式
 * 以键值对的形式出现
 */
public class WareHouse extends LinkedHashMap<String,Object> implements Serializable {
    //应该封装几个常用的通信关键字，如获取通信目的，获取传输元数据等
    //cmd，metaData
    public final static int NOTREADY=1,READY=0,EXCEPTION=-1;
    int status=READY;
    //private Exception expt = null;
    private boolean ready = true;
    private boolean mark = true;

    public WareHouse(){}
    public WareHouse(boolean ready){
        this.ready =ready;
        status = NOTREADY;
    }
    public WareHouse(String name,Object value){
        put(name,value);
    }
    public String getString(String key){
        return (String)get(key);
    }
    public void setString(String id, String str) {
        put(id,str);
    }
    public int getStatus() {
        return status;
    }

    public void wrap(String cmd,Class[] argTypes,Object[] args){
        put("cmd",cmd);
        put("argTypes",argTypes);
        put("args",args);
    }

    synchronized void setReady(int status)//, Exception expt
    {
        this.ready = true;
        //System.out.println("setReady status:"+status);
        this.status = status;
        //this.expt = expt;
    }

    public synchronized boolean isReady()//throws Throwable
    {
        return ready;
    }

    //TODO 暂时搞不懂setMark和getMark的含义
    public void setMark(boolean mark)
    {
        this.mark = mark;
    }

    public boolean getMark()
    {
        return mark;
    }

}
