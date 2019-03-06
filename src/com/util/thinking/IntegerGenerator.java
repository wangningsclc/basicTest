package com.util.thinking;

/**
 * Created by wn on 2018/8/23.
 */
public class IntegerGenerator implements  Generator<Integer> {
    private static int count;
    private final  int a = count ++;
    @Override
    public Integer next() {
        return count++;
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            System.out.println(new IntegerGenerator().next()+" "+ new IntegerGenerator().count);
        }
    }
}
