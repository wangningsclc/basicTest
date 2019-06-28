package com.util.test;

/**
 * @Auth wn
 * @Date 2019/6/27
 */
public class HQLTest {
    public static void main(String[] args) {
        String sql = " select * from LM_PM_SHD where LOAN_NO=? and PS_DUE_DT >= ? and PS_DUE_DT <= ? "
                + " order by PS_PERD_NO asc";
        System.out.println(sql);
    }

    public static String getDateIncDay(String inputDate, String dayCount,String operation) {
        StringBuffer subSql = new StringBuffer();
        subSql.append("to_char(to_date(").append(inputDate).append(",'").append("yyyy-MM-dd")
                .append("')").append(operation).append(dayCount).append(",'")
                .append("yyyy-MM-dd").append("') ");
        return subSql.toString();
    }
}
