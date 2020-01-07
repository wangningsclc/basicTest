package com.util.IMR;

import java.rmi.Remote;

/**
 * @Auth wn
 * @Date 2019/12/26
 */
public interface HelloFacade  extends Remote{

    String sayHello() throws Exception;
}
