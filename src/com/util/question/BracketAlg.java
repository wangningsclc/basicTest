package com.util.question;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auth wn
 * @Date 2020/5/9
 */
public class BracketAlg {

    //((((( ))))) 符合规范的集合
    public static void main(String[] args) {
//        System.out.println(checktmp("0000011111"));
        getList(6);
    }

    private static List<String> getList(int count) {
        List<String> list = new ArrayList<>();
        long sumAmt = 1;
        for (int i=0;i< count; i++ ) {
            sumAmt  = sumAmt << 1;
        }
        sumAmt--;
        for (long i =0;i<sumAmt; i++) {
            String tmp = Long.toBinaryString(i);
            int len = tmp.length();
            if (len < count) {
                for (int j=0;j<count-len;j++){
                    tmp = "0" + tmp;
                }
            }
            if(checktmp(tmp)){
                System.out.println(tmp.replaceAll("0", "(").replaceAll("1", ")"));
                list.add(tmp);
            }

        }
        System.out.println(list.size());
        return list;
    }

    private static boolean checktmp(String tmp) {
        if (tmp.length() == 0) {
            return true;
        }
        int last0 = tmp.lastIndexOf("0");
        if (last0 <0 ||last0 == tmp.length()-1) {
            return false;
        }
        int first1= tmp.substring(last0+1).indexOf("1");
        if (first1 < 0) {
            return false;
        }
        tmp = tmp.substring(0, last0) + tmp.substring(last0+2);
        return checktmp(tmp);
    }
}

