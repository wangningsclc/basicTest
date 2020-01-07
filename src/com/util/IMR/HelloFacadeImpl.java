package com.util.IMR;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @Auth wn
 * @Date 2019/12/26
 */
public class HelloFacadeImpl extends UnicastRemoteObject implements HelloFacade {
    protected HelloFacadeImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello() {
        System.out.println("hi,son");
        return "nice to meet you";
    }
}
