package com.util.thinking;


import java.util.Random;

/**
 * Created by wn on 2018/8/23.
 */
public class StringGenerator implements Generator<String>{
    String str = "abcdefghijklmnopqrstuvwxyz";
    @Override
    public String next(){
        return String.valueOf(str.charAt(new Random().nextInt(str.length())));
    }

    public static void main(String[] args) {
        int i= 0;
        while (i<100){
            System.out.println(new StringGenerator().next());
            i++;
        }
    }
}
