package com.example.myfxapp;

public class DadosSenhas {
    private final String SenhaEmail="ronttimrjzyhcpzh";
    private final String Email="furniturewoodpecker8@gmail.com";
    private final String dbName = "teste";
    private final String dbUser = "root";
    private final String dbPassword = "r0LP0t_?1T";
    private final String url = "jdbc:mysql://localhost:3306/" + dbName;
    private  static DadosSenhas INSTANCE;


    public static DadosSenhas get(){
        if(INSTANCE==null)
            INSTANCE=new DadosSenhas();

        return INSTANCE;
    }

    public String getSenhaEmail() {
        return SenhaEmail;
    }

    public String getEmail() {
        return Email;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getUrl() {
        return url;
    }

    public static DadosSenhas getINSTANCE() {
        return INSTANCE;
    }
}
