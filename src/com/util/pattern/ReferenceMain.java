package com.util.pattern;

import zookeeperDubbo.TimeUnit;

import java.lang.ref.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class ReferenceMain {

    int a = 0;
    public ReferenceMain(int a){
        this.a = a;
    }
    public ReferenceMain(){
    }
    //四种引用 强软弱虚
    public static void main(String[] args) throws Exception {
//        MainInferface m = new HardReference();
//        MainInferface m = new SoftRefer();
//        MainInferface m = new WeakRefer();
        MainInferface m = new PhantomRefer();
        m.mainMethod();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize" + a);
    }
}

interface MainInferface {
    void mainMethod() throws Exception;
}

//强引用 当对象不再被引用时才会被GC回收
class HardReference  implements  MainInferface{

    @Override
    public void mainMethod() throws Exception{
        ReferenceMain rm = new ReferenceMain();
        System.gc();
        System.out.println("有没回收");
        rm = null;
        System.gc();
    }
}

/**
 * 软引用 当内存空间被占满自动被回收
 * 用于缓存
 */
class SoftRefer implements MainInferface{

    @Override
    public void mainMethod() throws Exception {
        SoftReference<byte[]> sr = new SoftReference(new byte[1024*1024*600]);
        System.out.println(sr.get());
        System.gc();
        System.out.println(sr.get());//没被回收
        byte[]a = new byte[1024*1024*600];
        System.out.println(sr.get());//自动回收
    }
}



/**
 * 弱引用  JVM发生GC即被回收
 * 用于ThreadLocal 线程池 session等会用到
 * ThreadLocal原理：当起一个线程，调用threadLocal.set时会在内存中新建一个map对象（new ThreadLocalMap）
 * 当前线程中的ThreadLocal引用 保存在 Thread.ThreadLocalMap中Entry.key（弱引用）中
 * 当前线程创建多个ThreadLocal 则ThreadLocalMap中保存多个key
 * 当前线程强引用ThreadLocal，当不再引用ThreadLocal时，对象被GC回收，Entry.key值为null，若非弱引用则key值可能会造成内存泄露
 * 调用threadLocal.remove则 value 会被GC回收，不调用且当前线程一直不结束value值可能会造成内存泄漏
 * 当前线程结束则ThreadLocalMap中所有key value 都会被GC回收
 */
class WeakRefer implements MainInferface {
    ThreadLocal<ReferenceMain> threadLocal = new ThreadLocal<>();
    @Override
    public void mainMethod() throws Exception {
        WeakReference<ReferenceMain> wr = new WeakReference<ReferenceMain>(new ReferenceMain());
        System.out.println(wr.get());
        System.gc();
        System.out.println(wr.get());

        //ThreadLocal
           new Thread(()->{
               ThreadLocal<ReferenceMain> key2 = new ThreadLocal<>();

               threadLocal.set(new ReferenceMain(1));
               key2.set(new ReferenceMain(11));
               System.out.println(threadLocal.get());
               threadLocal.remove();
               System.gc();
           }).start();
        System.gc();
        new Thread(()->{

            threadLocal.set(new ReferenceMain(2));
            System.out.println(threadLocal.get());
        }).start();
        java.util.concurrent.TimeUnit.SECONDS.sleep(1);
        System.out.println(threadLocal.get());
        System.gc();
        java.util.concurrent.TimeUnit.SECONDS.sleep(1);
    }
}

/**
 * 虚引用 管理队外内存
 */
class PhantomRefer implements MainInferface{
    ReferenceQueue<ReferenceMain> QUEUE = new ReferenceQueue<>();
    @Override
    public void mainMethod() throws Exception {

        PhantomReference<ReferenceMain> pr = new PhantomReference(new ReferenceMain(),QUEUE);
        System.out.println(pr.get());
        System.out.println(QUEUE.poll());


        for (int i=1;i<100;i++) {
            System.gc();
            Reference reference= QUEUE.poll();
            if (reference != null) {
                System.out.println("GC次数："+i +" 回收引用：" + reference);
                break;
            }
        }
    }
}