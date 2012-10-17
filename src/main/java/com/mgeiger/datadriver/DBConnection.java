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

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public void setDbConnect(String dbConnect) {
        this.dbConnect = dbConnect;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getDbDriver() {
        return this.dbDriver;
    }

    public String getDbConnect() {
        return this.dbConnect;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }
    
    public boolean getConnect(){
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
            System.out.println(e.getMessage());
        }

        try {
            con = DriverManager.getConnection(
                    DBConnection.getInstance().getDbConnect(), DBConnection.getInstance().getUser(), DBConnection.getInstance().getPassword());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return con;
    }
}
