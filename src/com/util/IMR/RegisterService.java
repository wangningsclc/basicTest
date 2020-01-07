package com.util.IMR;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @Auth wn
 * @Date 2019/12/26
 */
public class RegisterService {

    public static void main(String[] args) {
        try {
            Registry registry  = LocateRegistry.createRegistry(9011);
            HelloFacade helloFacade = new HelloFacadeImpl();
            registry.rebind("helloRegister",  helloFacade);
            System.out.println("启动rmi服务");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
