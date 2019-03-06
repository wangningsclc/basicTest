package com.util.pattern.bridge;

/**
 * Created by wn on 2018/2/11.
 */
public interface PersonInterface {
    public void say();
}

class Zhaoyun implements PersonInterface{
    @Override
    public void say() {
        System.out.println("I'm zhaoyun");
    }
}
class Zhangfei implements PersonInterface{
    @Override
    public void say() {
        System.out.println("I'm zhangfei");
    }
}