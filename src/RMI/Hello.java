package RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Hello extends UnicastRemoteObject implements HelloImpl{
    protected Hello() throws RemoteException {
        super();
    }

    @Override
    public String sayHello(String name) throws RemoteException{
        String response = "Hello " + name;
        System.out.println(response);
        return response;
    }
}
