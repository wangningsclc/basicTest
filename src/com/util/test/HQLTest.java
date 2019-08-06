package com.util.test;

/**
 * @Auth wn
 * @Date 2019/6/27
 */
public class HQLTest {
    public static void main(String[] args) {
        StringBuffer subSql = new StringBuffer(30);
        subSql.append("select l");
        subSql.append(" from LmLoan l,LmLoanCont lc  ");
        subSql.append(" where l.loanNo=lc.loanNo");
        subSql.append(" and l.loanSts=?");
        subSql.append(" and ((l.loanDebtSts in (? , ?,?)");
        subSql.append(" and (");
        subSql.append(" (l.loanDevaInd='Y' ");
        subSql.append(" and l.loanDebtSts='NORM'");
        subSql.append(" and (l.loanDevaOrd is null or l.loanDevaOrd<>'I')");
        subSql.append(" ) or ");
        subSql.append(" (l.nextDueDt<=? or l.nextDueDt is null)");// 涓嬫搴旀墸鏃ユ湡
        subSql.append("))");
        //保险理赔借据
        subSql.append(" or (l.claimInd = 'Y' and exists (select 1 from LmClaimLoan cl where cl.relLoanNo = l.loanNo and cl.loanSts = 'ACTV')))");
        subSql.append(" and l.prcsPageDtInd=?");
        // 新增加的条件,不再已完成的状态
        // /不处理已经冻结的账号
        subSql.append(" and l.loanNo not in (");
        subSql.append("  select  la.loanNo from LmAtpyDetl la");
        subSql.append(" where la.atpyValDt=? and la.atpySeqNo=?");
        subSql.append(" and la.atpySts  in('CP','FZ'))");
        // sql.append(" and night=?");
        // 7*24小时还款数据，不再进行批扣
        subSql.append(" and l.loanNo not in (");
        subSql.append(" select i.loanNo from IntfLoanTxQue i ");
        subSql.append(" where i.loanNo=l.loanNo and i.prcsSts='INIT' ");
        subSql.append(" and i.txTyp='LNC4' and i.txDt=?) ");
        subSql.append( " and not exists (select 1 from LmAtpyDetlBegin b where b.id.loanNo = l.id.loanNo and b.id.atpyValDt = ?)");
        subSql.append(" order by l.loanNo");
        System.out.println(subSql);
    }

    public static String getDateIncDay(String inputDate, String dayCount,String operation) {
        StringBuffer subSql = new StringBuffer();
        subSql.append("to_char(to_date(").append(inputDate).append(",'").append("yyyy-MM-dd")
                .append("')").append(operation).append(dayCount).append(",'")
                .append("yyyy-MM-dd").append("') ");
        return subSql.toString();
    }

    public static String getDateIncMonth(String inputDate, String monthCount,String operation) {
        StringBuffer subSql = new StringBuffer();
        subSql.append("to_char(add_months(to_date(").append(inputDate).append(",'").append("yyyy-MM-dd")
                .append("'),").append(operation).append(monthCount).append("),'")
                .append("yyyy-MM-dd").append("') ");
        return subSql.toString();
    }

    public static String getNvl() {
        return "nvl";
    }
}
