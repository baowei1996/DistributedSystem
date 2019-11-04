package DCS;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * 包工头类
 */
public class Contractor {
    private Contractor ctor;
//    WorkerLocal[] wks = null;

    /**
     * 分配工作给工人，是被重载的部分
     * @param inhouse
     * @return
     */
    public WareHouse giveTask(WareHouse inhouse){
        return null;
    }
    public final WareHouse giveTask(WareHouse inhouse, boolean chainProcess)
    {
        WareHouse outhouse = giveTask(inhouse);
        if(chainProcess&&ctor!=null)
            return ctor.giveTask(outhouse, chainProcess);
        return outhouse;
    }

    /**
     * 获得特定工人类型的可用工人
     * @param workerType
     * @return
     */
    public WorkerLocal[] getWaitingWorkers(String workerType){
        ParkLocal parkLocal = BeanContext.getParkLocal("work");
        List<ObjectBean>objectBeanList = null;
        try {
            objectBeanList = parkLocal.get("_worker_"+workerType);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        List<WorkerLocal> wks = new ArrayList<>();
        for(ObjectBean ob:objectBeanList){
            Service service = (Service) ob.getObject();
            wks.add(new WorkerLocal(service.getHost(),service.getPort()));
        }
        return wks.toArray(new WorkerLocal[wks.size()]);
    }

    /**
     * 批处理的给工人分配工作
     * @param wks
     * @param wh
     * @return
     */
    public WareHouse[] doTaskBatch(WorkerLocal[] wks, WareHouse wh){
        WareHouse[] hmarr = new WareHouse[wks.length];
        for(int i=0,j=0;j<hmarr.length;){
            if(hmarr[i]==null){
                hmarr[i] = wks[i].doTask(wh);
            }
            else if(hmarr[i].isReady()){
                j++;
            }
            i=i+1==hmarr.length?0:i+1;
        }
        return hmarr;
    }

    public void doProject(WareHouse inhouse){

    }

    public Contractor toNext(Contractor ctor)
    {
        this.ctor = ctor;
        return ctor;
    }
}
