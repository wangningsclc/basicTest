package com.util.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wn on 2018/2/11.
 */
public class DynamicProxy implements InvocationHandler{
    private PersonInterface target;

    public DynamicProxy(PersonInterface personInterface){
        this.target = personInterface;
    }

    public Object getInstance(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("keep moving...");
        Object result = method.invoke(target,args);
        return result;
    }

    public static void main(String[] args) {
        PersonInterface personInterface = new PersonInterImpl();
        DynamicProxy proxy = new DynamicProxy(personInterface);
        PersonInterface proxyInstance = (PersonInterface) proxy.getInstance();
        System.out.println(proxyInstance.hunt());
    }
}
