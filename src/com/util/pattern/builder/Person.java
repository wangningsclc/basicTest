package com.util.pattern.builder;

/**
 * Created by wn on 2018/2/11.
 */
public interface Person {
}

class ManPerson implements  Person{
    public ManPerson(){
        System.out.println("I am a man...");
    }
}