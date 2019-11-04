package RMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args){
        try {
            String remoteAddr = "rmi://127.0.0.1:1099/Hello";
            HelloImpl hello = (HelloImpl) Naming.lookup(remoteAddr);    //非阻塞式
            String response = hello.sayHello("baowei");
            System.out.println(response);
            System.out.println("niaho");
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
