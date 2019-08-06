package com.util.test;


/**
 * Created by wn on 2017/12/13.
 */
public class Test {

    public static void main(String[] args) {
        StringBuffer sql = new StringBuffer();
        sql.append("select l from LmLoan l");
        sql.append(" where l.loanSts = ? and l.prcsPageDtInd=? and l.loanDebtSts in(?,?,?) ");
        sql.append(" and (l.atpySts != 'SK' or l.atpySts is null)");
        sql.append(" and (l.loanOdInd='Y' or l.loanStpAccInd='R')");
        sql.append(" and ((l.loanDebtSts='NORM' and ");
        sql.append(" ((l.loanGraceTyp='E' and ");
        sql.append(getDateIncDay("l.nextDueDt",
                "l.loanOdGrace", "+"));
        sql.append(" <=?) ");
        sql.append(" or ((l.loanGraceTyp='P' or l.loanGraceTyp is null) and l.nextDueDt <= ?))) ");// 转逾期增强
        sql.append(" or (l.loanDebtSts = 'DELQ' and exists ");
        sql.append(" (from LmPmShd ps where ps.id.loanNo = l.loanNo ");
        sql.append(" and ps.prcpState = '10' ");
        sql.append(" and ps.psDueDt <= ? and ps.id.psPerdNo <> 0 and ps.setlInd='N')))");// 后续期转逾期

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

