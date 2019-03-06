package com.util.thinking;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by wn on 2018/8/23.
 */
public class CollectionData<T> extends ArrayList<T> {

    public CollectionData(Generator<T> gen, int quantity){
        for(int i=0; i<quantity; i++){
            add(gen.next());
        }
    }

    public static <T> CollectionData<T> list(Generator<T> gen, int quantity){
        return new CollectionData<T>(gen, quantity);
    }

    public static void main(String[] args) {
        Set<String> set = new LinkedHashSet<>(
                new CollectionData<>(new StringGenerator(), 10)
        );
        System.out.println(set);
        Set<Integer> seti = new LinkedHashSet<>();
        seti.addAll(CollectionData.list(new IntegerGenerator(),10));
        System.out.println(seti);
    }
}
