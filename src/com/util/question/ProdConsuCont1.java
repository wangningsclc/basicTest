package com.util.question;

/**
 * @Auth wn
 * @Date 2020/5/14
 */
public class ProdConsuCont1 {

    public static void main(String[] args) {
        ArrList1_ arrList1_ = new ArrList1_();
        Thread[] producters = new Thread[5];
        for (int i=0;i<producters.length;i++){
            producters[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i<20;i++) {
                        arrList1_.put(String.valueOf(arrList1_.getCount()+1));
                    }
                }
            });
        }

        Thread[] consumers = new Thread[10];
        for (int i=0;i<consumers.length;i++){
            consumers[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i<10;i++) {
                        arrList1_.get();
                    }
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


class ArrList1_ {
    String[] list = new String[10];
    int index= 0;

    synchronized void put(String str) {
        while (index == 10) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("放入" +str);
        list[index++] = str;
        this.notifyAll();
    }

    synchronized String get(){
        while (index == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String res = list[--index];
        System.out.println("取出" +res);
        list[index] = null;
        this.notifyAll();
        return res;
    }

    int getCount() {
        return index;
    }
}