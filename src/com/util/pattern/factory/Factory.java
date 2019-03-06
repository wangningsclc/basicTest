package com.util.pattern.factory;

/**
 * Created by wn on 2018/2/9.
 */
public class Factory {

    public Car createCar(String type){
        switch (type){
            case "small":
                return new SmallCar();
            case "big":
                return new BigCar();
            default:
                break;
        }
        return null;
    }

    public static void main(String[] args) {
        Factory factory = new Factory();
        factory.createCar("small");
        factory.createCar("big");
    }
}
