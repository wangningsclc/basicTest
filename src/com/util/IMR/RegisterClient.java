package com.util.IMR;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @Auth wn
 * @Date 2019/12/26
 */
public class RegisterClient {
    public static void main(String[] args) {
        try {
            Registry registry  = LocateRegistry.getRegistry(9011);
            HelloFacade helloFacade  = (HelloFacade) registry.lookup("helloRegister");
            System.out.println(helloFacade.sayHello());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
