package com.example.myfxapp;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseHandler {
    public Connection dbLink;

    public Connection getConnection() {
        String dbName = "teste";
        String dbUser = "root";
        String dbPassword = "r0LP0t_?1T";
        String url = "jdbc:mysql://localhost:3306/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbLink = DriverManager.getConnection(url, dbUser, dbPassword);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return dbLink;
    }
}