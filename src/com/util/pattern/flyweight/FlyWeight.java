package com.util.pattern.flyweight;

/**
 * Created by wn on 2018/2/24.
 */
public abstract class FlyWeight {
    abstract void operate();
}
class ConcreateFlyWeight extends FlyWeight{
    public String name;

    public ConcreateFlyWeight(String name) {
        this.name = name;
    }

    @Override
    void operate() {
        System.out.println("concreate classs..."+name);
    }
}
