package com.util.pattern.strategy;

/**
 * Created by wn on 2018/2/24.
 */
public interface IStrategy {
    public void operate();
}

class PlanA implements IStrategy{
    @Override
    public void operate() {
        System.out.println("this is plan a...");
    }
}
class PlanB implements IStrategy{
    @Override
    public void operate() {
        System.out.println("this is plan b...");
    }
}