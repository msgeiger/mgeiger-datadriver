/*
 * @class QueryRunnerFixture
 */
package com.mgeiger.datadriver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @class QueryRunnerFixture
 */
public class QueryRunnerFixture {

    private String sql;
    private String column; // Single DB column in query e.g. select <column> from table
    private int numberOfColumns;
    private static boolean mapXmlParameters;
    private static Connection dbConnectionInstance;
    
    public static void QueryRunnerFixture() {
        QueryRunnerFixture.setMapXmlParameters();
        dbConnectionInstance = QueryRunnerFixture.connectToDatabase();
    }
    
    public void setSql(final String sqlString) {
        sql = sqlString;
    }

    public void setColumn(String columnString) {
        column = columnString;
    }

    public void setNumberOfColumns(final int countInt) {
        numberOfColumns = countInt;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }
    
    public static void setMapXmlParameters() {
        mapXmlParameters = XmlParser.useParser();
    }
    
    public static boolean getMapXmlParameters() {
        return mapXmlParameters;
    }
    
    public static Connection connectToDatabase() {
        final Connection con;
        
        if(QueryRunnerFixture.mapXmlParameters == true) {
            con = XmlParser.getMappedDbConfigurationConnection();
        } else {
            con = DBConnection.getDBConnection();
        }
        
        return con;
    }

    public int runQueryGetInt() throws SQLException {   
        try {
            final PreparedStatement s = dbConnectionInstance.prepareStatement(sql);
            final ResultSet result = s.executeQuery();
            
            if (!result.next()) {
                throw new IllegalStateException("Query must return at lease one row!");
            }

            return result.getInt(column);
        } finally {
            dbConnectionInstance.close();
        }
    }

    public String runQueryGetString() throws SQLException {     
        try {
            final PreparedStatement s = dbConnectionInstance.prepareStatement(sql);
            final ResultSet result = s.executeQuery();
            
            if (!result.next()) {
                throw new IllegalStateException("Query must return at lease one row!");
            }

            return result.getString(column);
        } finally {
            dbConnectionInstance.close();
        }
    }

    public List runQueryGetResults() throws SQLException {
        try {
            final PreparedStatement s = dbConnectionInstance.prepareStatement(sql);
            final ResultSet results = s.executeQuery();
            
            if (!results.next()) {
                throw new IllegalStateException("Query must return at lease one row!");
            }

            List rows = new ArrayList();
            int cnt = 0;
            
            while (results.next()) {
                List nrow = new ArrayList();
                
                for (int i = 1; i <= numberOfColumns; i++) 
                {
                    nrow.add(results.getObject(i));
                }

                rows.add(nrow);
            }

            return rows;
        } finally {
            dbConnectionInstance.close();
        }
    }

    public int runQueryUpdate() throws SQLException {
        try {
            final PreparedStatement s = dbConnectionInstance.prepareStatement(sql);
            int n = s.executeUpdate();
            
            if (n < 1) {
                throw new IllegalStateException("Update failed!");
            } else {
                n = 1;
                return n;
            }
        } finally {
            dbConnectionInstance.close();
        }
    }
}