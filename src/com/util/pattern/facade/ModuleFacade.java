package com.util.pattern.facade;

/**
 * Created by wn on 2018/2/11.
 */
public class ModuleFacade {
    public void getModule(){
        ModuleA a = new ModuleA();
        a.a();
        ModuleB b = new ModuleB();
        b.b();
        ModuleC c = new ModuleC();
        c.c();
    }

    public static void main(String[] args) {
        ModuleFacade facade = new ModuleFacade();
        facade.getModule();
    }
}
