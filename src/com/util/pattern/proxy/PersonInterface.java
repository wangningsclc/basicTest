package com.util.pattern.proxy;

/**
 * Created by wn on 2018/2/11.
 */
public interface PersonInterface {
    public String hunt();
}

class PersonInterImpl implements PersonInterface{
    @Override
    public String hunt() {
        System.out.println("hunting a deer ...");
        return "hunt";
    }
}
class  Person{
    public String hunt() {
        System.out.println("hunting nothing ...");
        return "person hunt";
    }
}