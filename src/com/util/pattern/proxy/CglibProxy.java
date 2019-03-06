package com.util.pattern.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by wn on 2018/2/11.
 */
public class CglibProxy implements MethodInterceptor{

    private Object target;
    public CglibProxy(Object target){
        this.target = target;
    }
    public Object getInstance(){
        Enhancer en = new Enhancer();
        en.setSuperclass(target.getClass());
        en.setCallback(this);
        return en.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("keep cglib...");
        Object result = method.invoke(target,objects);
        return result;
    }

    public static void main(String[] args) {
        PersonInterImpl personInter = new PersonInterImpl();
        CglibProxy proxy = new CglibProxy(personInter);
        PersonInterImpl personInter1 = (PersonInterImpl) proxy.getInstance();
        System.out.println(personInter1.hunt());
    }
}
