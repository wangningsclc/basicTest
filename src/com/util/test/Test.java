package com.util.test;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wn on 2017/12/13.
 */
public class Test {


    public static void main(String[] arg) throws IOException {
        String a = "asdf";
        System.out.println(a.substring(0,4));

    }
    public static BigDecimal getBigDecimalFromObject(Object object) {
        if (object != null) {
            return  new BigDecimal(String.valueOf(object));
        }
        return BigDecimal.ZERO;
    }
    public static String getSystemSeqNo(String wb, long seqNo) {
        String dateTime = getDateTime();
        String seqNoStr = String.valueOf(seqNo);
        System.out.println(seqNoStr.length());
        int seqNoLen = seqNoStr.length();
        if (seqNoLen< 12) {
            for (int i=0; i< 12-seqNoLen; i++) {
                seqNoStr = "0" + seqNoStr;
            }
        }
        return wb + dateTime + seqNoStr;
    }

    public static String getDateTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
}

