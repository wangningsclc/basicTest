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
    Phaser phaser = new Phaser();
    StampedLock stampedLock = new StampedLock();
    Semaphore semaphore = new Semaphore(10);
    Exchanger<String> exchanger = new Exchanger<>();
    LockSupport lockSupport;
    public static void main(String[] args) throws Exception {
//        MainInterface mainInterface = new SynClass();  //synchronized 同步锁
//        MainInterface mainInterface = new ReentrantLockClass();  //ReentrantLock 同步锁
//        MainInterface mainInterface = new ReadWriteLockClass();  //ReentrantReadWriteLock 读写锁  读写分离
        MainInterface mainInterface = new StampedLockClass();  //StampedLock 读写锁 (悲观 乐观)  读的同时可以写
//        MainInterface mainInterface = new AtomicIntClass();  //AtomicInteger 原子性递增
//        MainInterface mainInterface = new LongAdderClass();  //LongAdder 分段式锁 (分段累加 最后汇总)
//        MainInterface mainInterface = new CountDownClass();  //CountDownLatch 同步计数器
//        MainInterface mainInterface = new CyclicBarrierClass();  //CyclicBarrier 循环栅栏


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

class ReentrantLockClass implements MainInterface {

    int count = 0;

    ReentrantLock reentrantLock = new ReentrantLock();
    @Override
    public void mainMethod() throws Exception {
        long startTime =System.currentTimeMillis();
        Thread[] threads = new Thread[1000];
        for (int i=0;i<threads.length;i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i<10000;i++) {
                        try {
                            reentrantLock.lock();
                            count++;
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (reentrantLock!=null) {
                                reentrantLock.unlock();
                            }
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

class ReadWriteLockClass implements MainInterface {
    String msg = "origin";
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    @Override
    public void mainMethod() throws Exception {
        Thread[] reads = new Thread[10];
        for (int i=0;i<reads.length;i++) {
            reads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    readWriteLock.readLock().lock();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(msg);
                    readWriteLock.readLock().unlock();
                }
            });
        }

        Thread[] writes = new Thread[5];
        for (int i=0;i<writes.length;i++) {
            writes[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    readWriteLock.writeLock().lock();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    msg +="。";
                    System.out.println(msg);
                    readWriteLock.writeLock().unlock();
                }
            });
        }

        for (Thread thread: reads) {
            thread.start();
        }
        for (Thread thread: writes) {
            thread.start();
        }

    }
}

class StampedLockClass implements MainInterface {
    String msg = "origin";
    StampedLock stampedLock = new StampedLock();
    @Override
    public void mainMethod() throws Exception {
        Thread[] reads = new Thread[10];
        for (int i=0;i<reads.length;i++) {
            reads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    long l = -1;
                    l = stampedLock.tryOptimisticRead();  //获取乐观读锁
                    if (stampedLock.validate(l)){
                        l = stampedLock.readLock();
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(msg);
                        stampedLock.unlockRead(l);
                    }
                }
            });
        }

        Thread[] writes = new Thread[5];
        for (int i=0;i<writes.length;i++) {
            writes[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    long l = -1;
                    l = stampedLock.writeLock();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    msg += "。";
                    System.out.println(msg);
                    stampedLock.unlockWrite(l);
                }
            });
        }

        for (Thread thread: reads) {
            thread.start();
        }
        for (Thread thread: writes) {
            thread.start();
        }

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

class CyclicBarrierClass implements MainInterface {

    @Override
    public void mainMethod() throws Exception {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("满员 发车。。。");
            }
        });
        Thread[] threads = new Thread[30];
        for (int i=0;i<threads.length;i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("等待线程" + this);
                        cyclicBarrier.await();
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        for (Thread thread: threads) {
            thread.start();
        }
    }
}