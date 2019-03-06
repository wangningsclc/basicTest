package com.util.pattern.ProviderConsumer;

/**
 * Created by wn on 2018/3/28.
 */
public class Comsumer extends Thread{
    Basket basket;
    int i = 0;
    Comsumer(Basket basket, String name)
    {
        super(name);
        this.basket = basket;
    }

    @Override
    public void run() {
        for(int j=0;j<20;j++) {
            basket.getEggs();
        }
    }
}

class Provider extends Thread{
    Basket basket;
    int i = 0;
    Provider(Basket basket, String name){
        super(name);
        this.basket = basket;
    }
    @Override
    public void run() {
        for(int j=0;j<20;j++) {
            basket.putEggs("egg" + i++);
        }
    }

}

class Basket{
    String[] eggs;
    int count;
    int put;
    int get;
    Basket(int num){
        this.eggs = new String[num];
        this.count = 0;
        this.put = 0;
        this.get = 0;
    }

    public synchronized  void putEggs(String egg){
        try {
             while(count >= eggs.length){
                wait();
            }
            eggs[put] = egg;
            count++;
            System.out.println("人:"+Thread.currentThread().getName()+",篮子:"+put+",放入鸡蛋:"+egg);
            get = put;
            put = (put+1)%eggs.length;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized String getEggs(){
        try {
            while(count <=0){
                wait();
            }
            count--;
            String egg = eggs[get];
            System.out.println("人:"+Thread.currentThread().getName()+",篮子:"+get+",取走鸡蛋:"+egg);
            put = get;
            get = (get+eggs.length-1)%eggs.length;
            return egg;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Basket basket = new Basket(10);
        new Comsumer(basket,"消费者A").start();
        new Provider(basket,"生产者A").start();
        new Comsumer(basket,"消费者B").start();
        new Provider(basket,"生产者B").start();
        new Comsumer(basket,"消费者C").start();
        new Provider(basket,"生产者C").start();
    }
}
