package com.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlitData {
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:sqlite:/Users/wangning/Library/DBeaverData/workspace6/.metadata/sample-database-sqlite-1/Chinook.db";
        String sql = "select * from Customer c ";
        Connection conn =  DriverManager.getConnection(url);
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString("FirstName"));
        }
    }
}
