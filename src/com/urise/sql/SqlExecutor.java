package com.urise.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlExecutor {
    public void execute(PreparedStatement conn) throws SQLException;
}
