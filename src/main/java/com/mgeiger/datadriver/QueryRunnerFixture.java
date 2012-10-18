/*
 * @class QueryRunnerFixture
 */
package com.mgeiger.datadriver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @class QueryRunnerFixture
 */
public class QueryRunnerFixture {

    private String sql;
    private String column; // Single DB column in query e.g. select <column> from table

    public void setSql(final String sql) {
        this.sql = sql;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int runQueryGetInt() throws SQLException {
        final Connection c = DBConnection.getDBConnection();

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
        final Connection c = DBConnection.getDBConnection();

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

    public int runQueryUpdate() throws SQLException {
        final Connection c = DBConnection.getDBConnection();

        try {
            final PreparedStatement s = c.prepareStatement(sql);
            int n = s.executeUpdate();
            if (n < 1) {
                throw new IllegalStateException("Update failed!");
            }

            return n;
        } finally {
            c.close();
        }
    }
}