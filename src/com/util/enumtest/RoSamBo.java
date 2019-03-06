package com.util.enumtest;

/**
 * @Auth wn
 * @Date 2018/12/29
 */
public class RoSamBo {
    public static <T extends Competitor<T>> void match(T a, T b) {
        System.out.println(a+"vs." + b+ ":" + a.compete(b));
    }

    public static <T extends Enum<T> & Competitor<T>> void play(Class<T> rsbClass, int size){
        for(int i=0;i<size;i++){
        }
    }
}
