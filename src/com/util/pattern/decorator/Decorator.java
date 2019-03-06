package com.util.pattern.decorator;

/**
 * Created by wn on 2018/2/11.
 */
abstract class Decorator implements Person {
    private Person person;
    public Decorator(Person person){
        this.person = person;
    }

    @Override
    public void getName() {
        person.getName();
    }

    @Override
    public void getSkill() {
        person.getSkill();
    }
}

class Decorator_First extends Decorator{
    public Decorator_First(Person person) {
        super(person);
    }

    public void nameZhaoyun(){
        System.out.println("赵云。。。");
    }
    public void skillQiang(){
        System.out.println("枪");
    }
    @Override
    public void getName() {
        super.getName();
        nameZhaoyun();
    }

    @Override
    public void getSkill() {
        super.getSkill();
        skillQiang();
    }
}
class Decorator_Second extends Decorator{
    public Decorator_Second(Person person) {
        super(person);
    }

    public void nameZhaoyun(){
        System.out.println("常州赵子龙");
    }
    public void skillQiang(){
        System.out.println("七进七出");
    }
    @Override
    public void getName() {
        super.getName();
        nameZhaoyun();
    }

    @Override
    public void getSkill() {
        super.getSkill();
        skillQiang();
    }
}

class SanGuo implements Person{
    @Override
    public void getName() {
        System.out.println("我是谁？");
    }

    @Override
    public void getSkill() {
        System.out.println("我会什么？");
    }

    public static void main(String[] args) {
        Person person = new SanGuo();
        person.getName();
        Decorator decorator1 = new Decorator_First(person);
        decorator1.getName();
        Decorator decorator2 = new Decorator_Second(decorator1);
        decorator2.getName();
    }
}