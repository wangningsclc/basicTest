package com.util.pattern.flyweight;

import java.util.Hashtable;

/**
 * Created by wn on 2018/2/24.
 */
public class FlyWeightFactory {
    private Hashtable flyWeights = new Hashtable();
    public FlyWeight getFlyWeight(String name){
        FlyWeight flyWeight = (FlyWeight) flyWeights.get(name);
        if(flyWeight == null){
            flyWeight  = new ConcreateFlyWeight(name);
            flyWeights.put(name,flyWeight);
        }
        return flyWeight;
    }

    public int getSize(){
        return flyWeights.size();
    }

    public static void main(String[] args) {
        FlyWeightFactory flyWeightFactory = new FlyWeightFactory();
        FlyWeight f1,f2,f3,f4,f5;
        f1 = flyWeightFactory.getFlyWeight("today");
        f2 = flyWeightFactory.getFlyWeight("yesterday");
        f3 = flyWeightFactory.getFlyWeight("today");
        f4 = flyWeightFactory.getFlyWeight("today");
        f5 = flyWeightFactory.getFlyWeight("today");
        System.out.println(flyWeightFactory.getSize());
        f1.operate();
        f2.operate();
        f3.operate();
        f4.operate();
        f5.operate();
    }
}
