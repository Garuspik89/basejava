package com.urise.sql;

import com.Config;
import com.urise.exception.StorageException;

import java.sql.*;

public class SqlHelper implements ConnectionFactory{

    public PreparedStatement ps;

    public PreparedStatement getPs() {
        return ps;
    }

    @Override
    public Connection getConnection()  {
        try {
            return DriverManager.getConnection(Config.get().getDbURL(), Config.get().getDbUser(), Config.get().getDbPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void preparedStatement(String command) {
        try
        {
            Connection conn = getConnection();
            ps = conn.prepareStatement(command);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}

