package com.util.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.*;

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
}
