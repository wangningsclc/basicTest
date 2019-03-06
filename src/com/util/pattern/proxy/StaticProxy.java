package com.util.pattern.proxy;

/**
 * Created by wn on 2018/2/11.
 */
public class StaticProxy implements  PersonInterface{
    PersonInterface personInterface;
    public StaticProxy(PersonInterface personInterface){
        this.personInterface = personInterface;
    }
    @Override
    public String hunt() {
        System.out.println("keep silence...");
        personInterface.hunt();
        return "static hunt";
    }

    public static void main(String[] args) {
        PersonInterface person = new PersonInterImpl();
        StaticProxy proxy = new StaticProxy(person);
        System.out.println(proxy.hunt());
    }
}
