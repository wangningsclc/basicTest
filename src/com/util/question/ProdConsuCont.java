package com.util.question;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 设计一个容器，支持put get getCount 方法，满足两个生产者 二十个消费者阻塞调用
 * @Date 2020/5/14
 */
public class ProdConsuCont {

    static  ReentrantLock lock = new ReentrantLock();
    static Condition prodCond = lock.newCondition();
    static Condition consCond = lock.newCondition();
    public static void main(String[] args) {
        ArrList_ list = new ArrList_();

        Thread[] producters = new Thread[2];
        for (int i=0;i<producters.length;i++){
            producters[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    for (int i=0;i<10;i++) {
                        while (list.getCount() == 10) {
                            try {
                                prodCond.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        list.put(String.valueOf(list.getCount()+1) );
                        if (list.getCount() == 1) {
                            consCond.signal();
                        }
                    }
                    lock.unlock();
                }
            });
        }

        Thread[] consumers = new Thread[20];
        for (int i=0;i<consumers.length;i++){
            consumers[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    while (list.getCount() == 0) {
                        try {
                            consCond.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    list.get();
                    if (list.getCount() == 9) {
                        prodCond.signal();
                    }
                    lock.unlock();
                }
            });
        }

        for (Thread prod: producters){
            prod.start();
        }
        for (Thread consumer: consumers){
            consumer.start();
        }
    }
}

class ArrList_ {
    String[] list = new String[10];
    int index= 0;

    void put(String str) {
        System.out.println("放入" +str);
        list[index++] = str;
    }

    String get(){
        String res = list[--index];
        System.out.println("取出" +res);
        list[index] = null;
        return res;
    }

    int getCount() {
        return index;
    }
}