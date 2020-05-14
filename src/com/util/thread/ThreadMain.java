package com.util.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.*;

/**
 * @Date 2020/5/12
 */
public class ThreadMain {
    public static void main(String[] args) throws Exception {
//        MainInterface mainInterface = new SynClass();  //synchronized 同步锁
//        MainInterface mainInterface = new ReentrantLockClass();  //ReentrantLock 同步锁
//        MainInterface mainInterface = new ConditionClass();  //Condition 线程通讯
//        MainInterface mainInterface = new ReadWriteLockClass();  //ReentrantReadWriteLock 读写锁  读写分离
//        MainInterface mainInterface = new StampedLockClass();  //StampedLock 读写锁 (悲观 乐观)  读的同时可以写
//        MainInterface mainInterface = new AtomicIntClass();  //AtomicInteger 原子性递增
//        MainInterface mainInterface = new LongAdderClass();  //LongAdder 分段式锁 (分段累加 最后汇总)
//        MainInterface mainInterface = new CountDownClass();  //CountDownLatch 同步计数器
//        MainInterface mainInterface = new CyclicBarrierClass();  //CyclicBarrier 循环栅栏
//        MainInterface mainInterface = new PhaserClass(); // Phaser 阶段器  类似于循环栅栏 但是可以更精确的分段控制
//        MainInterface mainInterface = new SemaphoreClass(); //计数信号器 （限流作用）
//        MainInterface mainInterface = new ExchangerClass();
        MainInterface mainInterface = new LockSupportClass();
//        mainInterface.mainMethod();

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

class ConditionClass implements MainInterface {
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();

    @Override
    public void mainMethod() throws Exception {

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("线程1等待");
                try {
                    condition1.await();
                    System.out.println("线程1继续");
                    condition2.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("线程2等待");
                try {
                    condition1.signal();
                    condition2.await();
                    System.out.println("线程2继续");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        }).start();

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
                    System.out.println("R" +msg);
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
                    System.out.println("W" + msg);
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
                    while (stampedLock.validate(l)){
                    }
                    l = stampedLock.readLock();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("R" + msg);
                    stampedLock.unlockRead(l);
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
                    System.out.println("W" + msg);
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


class PhaserClass extends Phaser implements MainInterface {


    @Override
    public void mainMethod() throws Exception {
        Phaser phaser = new PhaserClass();
        Thread[] threads = new Thread[5];
        for (int i=0;i<threads.length;i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "到达赛场");
                    phaser.arriveAndAwaitAdvance();
                    System.out.println(Thread.currentThread().getName() + "第一场比赛结束");
                    phaser.arriveAndAwaitAdvance();
                    System.out.println(Thread.currentThread().getName() + "第二场比赛结束");
                    phaser.arriveAndAwaitAdvance();
                    System.out.println(Thread.currentThread().getName() + "第三场比赛结束");

                }
            }, "运动员"+i);
            phaser.register();
        }

        for (Thread thread: threads) {
            thread.start();
        }
    }

    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case 0:
                System.out.println("第一场比赛ING。。。");
                return false;
            case 1:
                System.out.println("第二场比赛ING。。。");
                return false;
            case 2:
                System.out.println("第三场比赛ING。。。");
                return true;
            default:
                return true;

        }
    }
}

class SemaphoreClass implements MainInterface {

    Semaphore semaphore = new Semaphore(2);
    @Override
    public void mainMethod() throws Exception {
        Thread[] threads = new Thread[10];
        for (int i=0;i<threads.length;i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println("限流车辆" + this);
                        TimeUnit.SECONDS.sleep(1);
                        semaphore.release();
                    } catch (Exception e) {
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

class ExchangerClass implements MainInterface {
    Exchanger<String> exchanger = new Exchanger<>();

    @Override
    public void mainMethod() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String equitment = "屠龙刀";
                System.out.println(this +equitment);
                try {
                    equitment = exchanger.exchange(equitment);
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(this + equitment);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String equitment = "倚天剑";
                System.out.println(this +equitment);
                try {
                    equitment = exchanger.exchange(equitment);
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(this + equitment);
            }
        }).start();
    }
}

class LockSupportClass implements MainInterface {
    String[] a = new String[]{"a","b","c","d","e","f"};
    String[] b = new String[]{"1","2","3","4","5","6"};
    Thread t1,t2;

    @Override
    public void mainMethod() throws Exception {

        t1 = new Thread(new Runnable() {
             @Override
             public void run() {
                 for (String str: a) {
                     System.out.printf(str);
                     LockSupport.unpark(t2);
                     LockSupport.park();
                 }
             }
         });
        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (String str: b) {
                    LockSupport.park();
                    System.out.printf(str);
                    LockSupport.unpark(t1);
                }
            }
        });
        t1.start();
        t2.start();
    }
}