package com.util.pattern.singleton;

/**
 * Created by wn on 2018/2/9.
 */
public class HungrySingleton {
    private static final  HungrySingleton INSTANCE = new HungrySingleton();
    private HungrySingleton(){

    }
    public static HungrySingleton getInstance(){
        return INSTANCE;
    }
}
