package com.util.pattern.builder;

/**
 * Created by wn on 2018/2/11.
 * 建造者模式
 */
public class PersonDirector {
    public Person constructPerson(PersonBuilder personBuilder){
        personBuilder.buildBody();
        personBuilder.buildFood();
        personBuilder.buildHead();
        return personBuilder.buildPerson();
    }

    public static void main(String[] args) {
        PersonDirector director = new PersonDirector();
        director.constructPerson(new ManBuilder());
    }
}
