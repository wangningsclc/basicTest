package com.util.test;


import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wn on 2017/12/14.
 */
public class Person {
    private String name;

    private BigDecimal money;

    private Date birthDay;
    private Integer age;
    private int height;
    void getFridenly(){

    }
    private void getPrivate(){

    }
    protected void getProtected(){

    }
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Person(){
        System.out.println("before person");
        getI();
        System.out.println("after person");
    }
    public Person(String name, BigDecimal money, Integer age) {
        this.name = name;
        this.money = money;
        this.age = age;
    }

    public Person(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        System.out.println(list.stream().count());
        list.add(new Person("tom", new BigDecimal("1.1"),1));
        list.add(new Person("tom",new BigDecimal("1.3"),3));
        list.add(new Person("lucy",new BigDecimal("1.2"),5));
        list.add(new Person("lily",new BigDecimal("1.2"),7));
        System.out.println(list.stream().map(Person::getName).collect(Collectors.toList()));
//        Map<String, List<Person>> map = list.stream().collect(Collectors.groupingBy(p ->p.getName().substring(0,1)));
//        System.out.println(map);
//        Person p = list.stream().max((p1, p2) -> p1.getMoney().compareTo(p2.getMoney())).get();
//        System.out.println(p.getMoney());
//        Long a = list.stream().filter(pa->pa.getAge() == 9).count();
//        System.out.println(a);
//        BigDecimal amount = list.stream().map(Person::getMoney).reduce(BigDecimal::add).orElse(new BigDecimal(111));
//        list.stream().forEach(l->l.setAge(222));
//        System.out.println("------");
//        System.out.println(list.get(0));
    }

    private static void getAge(Person person) {
        int age = person.getAge();
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int i;
    private    int getI(){
        System.out.println("person i "+i);
        return i;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
