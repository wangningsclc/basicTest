package com.util.test;


/**
 * Created by wn on 2017/12/13.
 */
public class Test {

    public static void main(String[] args) {
        try {
            try {
                int i = 0;
                int a = 10 / i;
            } catch (Exception e) {
                throw new Exception("错误1", e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDateIncDay(String inputDate, String dayCount,String operation) {
        StringBuffer subSql = new StringBuffer();
            subSql.append("to_char(to_date(").append(inputDate).append(",'").append("yyyy-MM-dd")
                    .append("')").append(operation).append(dayCount).append(",'")
                    .append("yyyy-MM-dd").append("') ");
        return subSql.toString();
    }
}

