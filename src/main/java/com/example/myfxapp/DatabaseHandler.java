package com.example.myfxapp;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseHandler {
    public Connection dbLink;

    public Connection getConnection() {
        String dbName = DadosSenhas.get().getDbName();
        String dbUser = DadosSenhas.get().getDbUser();
        String dbPassword = DadosSenhas.get().getDbPassword();
        String url = DadosSenhas.get().getUrl();



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
