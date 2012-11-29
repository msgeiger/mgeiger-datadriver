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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @class QueryRunnerFixture
 */
public class QueryRunnerFixture {

    private String sql;
    private String column; // Single DB column in query e.g. select <column> from table
    private int numberOfColumns = 1;

    public static Connection setDbConnection() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QueryRunnerFixture.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        XmlParser.getXmlConfigurationDbConnection();
        final Connection c = DBConnection.getDBConnection();

        return c;
    }

    public void setSql(final String sql) {
        this.sql = sql;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public void setNumberOfColumns(final int count) {
        this.numberOfColumns = count;
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public int runQueryGetInt() throws SQLException {
        Connection c = QueryRunnerFixture.setDbConnection();

        try {
            final PreparedStatement s = c.prepareStatement(sql);
            final ResultSet result = s.executeQuery();
            if (!result.next()) {
                throw new IllegalStateException("Query must return at lease one row!");
            }

            return result.getInt(column);
        } finally {
            c.close();
        }
    }

    public String runQueryGetString() throws SQLException {
        Connection c = QueryRunnerFixture.setDbConnection();

        try {
            final PreparedStatement s = c.prepareStatement(sql);
            final ResultSet result = s.executeQuery();
            if (!result.next()) {
                throw new IllegalStateException("Query must return at lease one row!");
            }

            return result.getString(column);
        } finally {
            c.close();
        }
    }

    public List runQueryGetResults() throws SQLException {
        Connection c = QueryRunnerFixture.setDbConnection();

        try {
            final PreparedStatement s = c.prepareStatement(sql);
            final ResultSet results = s.executeQuery();
            if (!results.next()) {
                throw new IllegalStateException("Query must return at lease one row!");
            }

            //ResultSetMetaData metaData = results.getMetaData();
            List rows = new ArrayList();

            while (results.next()) {
                List nrow = new ArrayList();
                for (int i = 1; i <= numberOfColumns; i++) //replace 3 with the length of the columns
                {
                    nrow.add(results.getObject(i));
                }

                rows.add(nrow);
            }

            return rows;
        } finally {
            c.close();
        }
    }

    public int runQueryUpdate() throws SQLException {
        Connection c = QueryRunnerFixture.setDbConnection();

        try {
            final PreparedStatement s = c.prepareStatement(sql);
            int n = s.executeUpdate();
            if (n < 1) {
                throw new IllegalStateException("Update failed!");
            } else {
                n = 1;
                return n;
            }
        } finally {
            c.close();
        }
    }
    
}