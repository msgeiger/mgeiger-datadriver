/*
 * @class DBConnection
 */
package com.mgeiger.datadriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @class DBConnection
 */
public class DBConnection {

    private boolean parseXmlValues = false;
    private String dbDriver;
    private String dbConnect;
    private String user;
    private String password;
    public static DBConnection INSTANCE;

    public static DBConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DBConnection();
        }

        return INSTANCE;
    }

    public void setParseXmlValues(boolean answer) {
        parseXmlValues = answer;
    }

    public void setDbDriver(String dbDriverString) {
        dbDriver = dbDriverString;
    }

    public void setDbConnect(String dbConnectString) {
        dbConnect = dbConnectString;
    }

    public void setUser(String userString) {
        user = userString;
    }

    public void setPassword(String passwordString) {
        password = passwordString;
    }

    public boolean getParseXmlValues() {
        return parseXmlValues;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getDbConnect() {
        return dbConnect;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public boolean getConnect() {
        DBConnection.getInstance().setDbDriver(dbDriver);
        DBConnection.getInstance().setDbConnect(dbConnect);
        DBConnection.getInstance().setUser(user);
        DBConnection.getInstance().setPassword(password);

        return true;
    }

    public static Connection getDBConnection() {
        Connection con = null;

        try {
            Class.forName(DBConnection.getInstance().dbDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            con = DriverManager.getConnection(
                    DBConnection.getInstance().getDbConnect(), DBConnection.getInstance().getUser(), DBConnection.getInstance().getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }
}