package com.util.pattern.builder;

/**
 * Created by wn on 2018/2/11.
 */
public interface PersonBuilder {
    public void buildHead();
    public void buildBody();
    public void buildFood();
    public Person buildPerson();
}

class ManBuilder implements PersonBuilder{
    Person person;
    public ManBuilder(){
        person = new ManPerson();
    }
    @Override
    public void buildHead() {
        System.out.println("build man's head...");
    }

    @Override
    public void buildBody() {
        System.out.println("build man's body");
    }

    @Override
    public void buildFood() {
        System.out.println("build man's foot");
    }

    @Override
    public Person buildPerson() {
        return person;
    }
}