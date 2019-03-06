package com.util.test;


/**
 * Created by wn on 2017/12/13.
 */
public class Test{

    public static void main(String[] args) {
        Thread thread  = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("...");
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
