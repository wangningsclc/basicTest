package com.util.pattern.singleton;

/**
 * Created by wn on 2018/2/9.
 */
public class LazySingleton {
    private static class LazyHolder{
        private static final  LazySingleton INSTANCE = new LazySingleton();
    }
    private LazySingleton(){

    }
    public static LazySingleton getInstance(){
        return LazyHolder.INSTANCE;
    }
}
