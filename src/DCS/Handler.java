package DCS;

import java.lang.reflect.Method;

/**
 * 动态代理工人的doTask任务
 */
public class Handler{
    Object target;
    public Handler(Object target) {
        this.target = target;
    }

    public WareHouse process(WareHouse inhouse){
        final WareHouse outhouse = new WareHouse();
        try{
            String methodName = inhouse.getString("cmd");
            Class[] argTypes = (Class[])inhouse.get("argTypes");
            Object[] args = (Object[]) inhouse.get("args");
            Method method = target.getClass().getMethod(methodName,argTypes);
            Object result = method.invoke(target,args);
            if(result!=null)
                outhouse.put("result",result);
            outhouse.setReady(WareHouse.READY);
        }catch(Throwable e){
            outhouse.setReady(WareHouse.EXCEPTION);
        }
        return outhouse;
    }
}
