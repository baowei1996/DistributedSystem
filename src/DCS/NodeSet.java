package DCS;

import java.util.HashMap;
import java.util.Map;

public class NodeSet {
    Map<String, Map<String,ObjectBean>> objectBeans = new HashMap<String, Map<String, ObjectBean>>();

    public synchronized Map<String,ObjectBean> get(String domain){
        return objectBeans.get(domain);
    }
    public synchronized ObjectBean get(String domain,String node){
        ObjectBean ob = null;
        Map map = objectBeans.get(domain);
        if(map != null){
            ob = (ObjectBean)map.get(node);
        }
        return ob;
    }
    public synchronized void add(String domain,String node,ObjectBean obj){
        Map map = objectBeans.get(domain);
        if(map == null){
            map = new HashMap<String,ObjectBean>();
        }
        map.put(node,obj);
        objectBeans.put(domain,map);
    }

    public synchronized void delete(String domain,String node){
        Map map = objectBeans.get(domain);
        if(map != null){
            map.remove(node);
        }
        if(map == null || map.size()==0){
            objectBeans.remove(domain);
        }
    }
}
