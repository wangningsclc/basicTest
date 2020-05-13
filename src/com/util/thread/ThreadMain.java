package com.util.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.*;

/**
 * @Date 2020/5/12
 */
public class ThreadMain {
    //synchronized
    //volatile
    //JUC中同步锁  java.util.concurent
    ReentrantLock reentrantLock = new ReentrantLock();
    AtomicInteger atomicInteger = new AtomicInteger();
    LongAdder longAdder = new LongAdder();
    CountDownLatch countDownLatch = new CountDownLatch(10);
    CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new Runnable() {
        @Override
        public void run() {
            System.out.println("满员 发车。。。");
        }
    });

    Phaser phaser = new Phaser();

    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    StampedLock stampedLock = new StampedLock();
    Semaphore semaphore = new Semaphore(10);
    Exchanger<String> exchanger = new Exchanger<>();
    LockSupport lockSupport;
    public static void main(String[] args) throws Exception {
//        MainInterface mainInterface = new SynClass();  //synchronized 同步锁
//        MainInterface mainInterface = new AtomicIntClass();  //AtomicInteger 原子性递增
//        MainInterface mainInterface = new LongAdderClass();  //LongAdder 分段式锁 (分段累加 最后汇总)
        MainInterface mainInterface = new CountDownClass();
        mainInterface.mainMethod();

    }

}

interface MainInterface {
    void mainMethod() throws Exception;
}

class SynClass implements MainInterface {

    Object o = new Object();
    int count = 0;

    @Override
    public void mainMethod() throws Exception {
        long startTime =System.currentTimeMillis();
        Thread[] threads = new Thread[1000];
        for (int i=0;i<threads.length;i++) {
            threads[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i<10000;i++) {
                            synchronized (o){
                                count++;
                            }
                        }
                    }
            });
        }
        for (Thread thread: threads) {
            thread.start();
        }
        for (Thread thread: threads) {
            thread.join();
        }


        long endTime =System.currentTimeMillis();
        System.out.println("synchronized :" + count + " 消耗时间：" + (endTime - startTime) + "ms");
    }
}

class AtomicIntClass implements MainInterface {

    AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void mainMethod() throws Exception {
        long startTime =System.currentTimeMillis();
        Thread[] threads = new Thread[1000];
        for (int i=0;i<threads.length;i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i<10000;i++) {
                        atomicInteger.incrementAndGet();
                    }
                }
            });
        }
        for (Thread thread: threads) {
            thread.start();
        }
        for (Thread thread: threads) {
            thread.join();
        }
        long endTime =System.currentTimeMillis();
        System.out.println("AtomicInteger :" + atomicInteger.get() + " 消耗时间：" + (endTime-startTime) + "ms");
    }
}

class  LongAdderClass implements MainInterface {

    LongAdder longAdder = new LongAdder();

    @Override
    public void mainMethod()  throws Exception {
        long startTime = System.currentTimeMillis();
        Thread[] threads = new Thread[1000];
        for (int i=0;i<threads.length;i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i<10000;i++) {
                        longAdder.increment();
                    }
                }
            });
        }
        for (Thread thread: threads) {
            thread.start();
        }
        for (Thread thread: threads) {
            thread.join();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("LongAdder :" + longAdder.longValue() + " 消耗时间：" + (endTime-startTime) + "ms");
    }
}

class CountDownClass implements MainInterface {

    CountDownLatch countDownLatch = new CountDownLatch(1000);
    int count = 0;
    @Override
    public void mainMethod() throws Exception {
        long startTime =System.currentTimeMillis();
        Thread[] threads = new Thread[1000];
        for (int i=0;i<threads.length;i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i<10000;i++) {
                        synchronized (this.getClass()){
                            count++;
                        }
                    }
                    countDownLatch.countDown();
                }
            });
        }
        for (Thread thread: threads) {
            thread.start();
        }
        countDownLatch.await();
        long endTime =System.currentTimeMillis();
        System.out.println("CountDownLatch :" + count + " 消耗时间：" + (endTime-startTime) + "ms");
    }
}