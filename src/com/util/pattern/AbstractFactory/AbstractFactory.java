package com.util.pattern.AbstractFactory;

public class AbstractFactory {
    public static void main(String[] args) {
            AbstractFactcy abstractFactcy = new ManFadctory();
            Body body = abstractFactcy.createBody();
            Foot foot = abstractFactcy.crerateFoot();
            body.printBody();
            foot.printFoot();
    }
}

interface AbstractFactcy {
    Body createBody();
    Foot crerateFoot();
}
class ManFadctory implements AbstractFactcy{

    @Override
    public Body createBody() {
       return new ManBody();
    }

    @Override
    public Foot crerateFoot() {
        return new ManFoot();
    }
}

interface Body {
    void printBody();
}
interface Foot{
    void printFoot();
}

class ManBody implements Body {

    @Override
    public void printBody() {
        System.out.println("man's body");
    }
}

class ManFoot implements Foot {

    @Override
    public void printFoot() {
        System.out.println("man's foot");
    }
}