package com.tangailquiz.dao;

import com.tangailquiz.util.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Opens a MySQL connection.
 * Every DAO calls DBConnection.getConnection().
 * No other class should talk to DriverManager directly.
 */
public class DBConnection {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("MySQL JDBC driver not found.");
        }
    }

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        String url = AppConfig.get("db.url");
        String user = AppConfig.get("db.user");
        String password = AppConfig.get("db.password");
        return DriverManager.getConnection(url, user, password);
    }
}
