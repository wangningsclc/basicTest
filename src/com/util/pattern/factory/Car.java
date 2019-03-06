package com.util.pattern.factory;

/**
 * Created by wn on 2018/2/9.
 */
public interface Car {
}

class BigCar implements Car{
    public BigCar(){
        System.out.println("create big car ...");
    }
}

class SmallCar implements Car{
    public SmallCar(){
        System.out.println("create small car...");
    }
}