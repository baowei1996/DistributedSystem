package HB;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HeartbeatHandler extends Remote {
         boolean sendHeartBeat(HeartbeatEntity info) throws RemoteException;
         }
