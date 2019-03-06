package com.util.pattern.bridge;

/**
 * Created by wn on 2018/2/11.
 */
public abstract class AbstractBridge {
    PersonInterface personInterface;

    public PersonInterface getPersonInterface() {
        return personInterface;
    }

    public void setPersonInterface(PersonInterface personInterface) {
        this.personInterface = personInterface;
    }
    public void say(){
        personInterface.say();
    }
}
class Bridge extends AbstractBridge{
    @Override
    public void say() {
        super.say();
    }

    public static void main(String[] args) {
        AbstractBridge bridge = new Bridge();
        PersonInterface zhaoyun = new Zhaoyun();
        bridge.setPersonInterface(zhaoyun);
        bridge.say();
        PersonInterface zhangfei = new Zhangfei();
        bridge.setPersonInterface(zhangfei);
        bridge.say();
    }
}
