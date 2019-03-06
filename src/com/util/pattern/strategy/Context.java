package com.util.pattern.strategy;

/**
 * Created by wn on 2018/2/24.
 */
public class Context {
    private IStrategy strategy;

    public Context(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void operate(){
        strategy.operate();
    }
    public static void main(String[] args) {
        Context c1 = new Context(new PlanA());
        c1.operate();
        c1 = new Context(new PlanB());
        c1.operate();
    }
}
