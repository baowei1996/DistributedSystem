package RMI;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args){
        try{
            LocateRegistry.createRegistry(1099);
            HelloImpl hello = new Hello();
            Naming.bind("rmi://127.0.0.1:1099/Hello",hello);
            for(int i = 1;i<100;i++){
                System.out.println(i);
            }
        }catch (RemoteException | AlreadyBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
