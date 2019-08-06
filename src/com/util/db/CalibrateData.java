package com.util.db;


import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

/**
 * @Auth wn
 * @Date 2019/8/5
 */
public class CalibrateData {

    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        calibrateTheData();
    }

    private static void calibrateTheData() {
        Connection newConn = null;
        Connection oldConn = null;
        try {
            List<String> sqls = getSqls();
            newConn = getNewConnection();
            oldConn = getOldConnection();
            Map<String, List<String>> dataRes = new HashMap<>();
            for (String sql : sqls) {
                String tableName = getTableName(sql);
                ResultSet rsnew = newConn.createStatement().executeQuery(sql);
                ResultSet rsold = oldConn.createStatement().executeQuery(sql);
                ResultSetMetaData orsm =  rsnew.getMetaData();
                List<String> columns = new ArrayList<>();
                for (int i = 1; i<= orsm.getColumnCount(); i++) {
                    columns.add(orsm.getColumnName(i));
                }
                List<List<String>> datanew = getData(rsnew, columns);
                List<List<String>> dataold = getData(rsold, columns);
                if (datanew.size() != dataold.size()) {
                    dataRes.put(tableName, Arrays.asList(new String [] {"行数不对应"}));
                    continue;
                }
                List<String> errMsgs = new ArrayList<>();
                for (int i = 0; i<datanew.size(); i++) {
                    String errMsg = "";
                    List<String> row = datanew.get(i);
                    for (int j=0; j<row.size(); j++) {
                        String newAtom= row.get(j);
                        String oldAtom = dataold.get(i).get(j);
                        if (!compare(newAtom, oldAtom)) {
                            String field = columns.get(j);
                            if (isDynamicData(field)) {
                                continue;
                            }
                            errMsg += "," + field;
                        }
                    }
                    if (errMsg.length() > 0) {
                        errMsg = errMsg.substring(1);
                        errMsg = "第" +(i + 1) + "行差错字段:" + errMsg;
                        errMsgs.add(errMsg);
                    }
                }
                if (errMsgs.size() == 0) {
                    errMsgs.add("数据正确");
                }
                dataRes.put(tableName, errMsgs);
            }
            System.out.println(dataRes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (newConn != null) {
                    newConn.close();
                }
                if (oldConn != null) {
                    oldConn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    private static String getTableName(String sql) {
        sql = sql.toLowerCase();
        int i = sql.indexOf("from");
        sql = sql.substring(i+4).trim();
        int blank = sql.indexOf(" ");
        return sql.substring(0, blank);
    }

    private static boolean compare(String newAtom, String oldAtom) {
        return  Strings.nullToEmpty(newAtom).equals(Strings.nullToEmpty(oldAtom));
    }

    private static List<List<String>> getData(ResultSet rs, List<String> columns) throws Exception{
        List<List<String>> data = new ArrayList<>();
        while (rs.next()) {
            List<String> row = new ArrayList<>();
            for (String col :columns) {
                row.add(rs.getString(col));
            }
            data.add(row);
        }
        return data;
    }

    private static boolean isDynamicData(String field) {
        field = field.toLowerCase();
        List<String> list = Arrays.asList(new String[]{"up_ver","tx_log_seq","crt_dt"});
        return list.contains(field);
    }

    public static Connection getNewConnection() throws Exception{
        String url = "jdbc:oracle:thin:@20.4.16.210:1521:cmisdb";
        String username = "wangning";
        String password = "glloans123";
        return DriverManager.getConnection(url, username, password);
    }

    public static Connection getOldConnection() throws Exception{
        String url = "jdbc:oracle:thin:@20.4.16.210:1521:cmisdb";
        String username = "wangningo";
        String password = "glloans123";
        return DriverManager.getConnection(url, username, password);
    }

    public static List<String> getSqls() throws Exception{
        InputStream str = new FileInputStream("E:\\work_test\\basicTest\\src\\com\\util\\db\\sql.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(str));
        String line = null;
        List<String> sqls = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (line.length() >0 && line.indexOf(';') > 0) {
                line = line.substring(0, line.indexOf(';'));
            }
            sqls.add(line);
        }
        return sqls;
    }
}
