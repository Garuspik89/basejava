package com.urise.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlExecutor<T>{
    public T execute(PreparedStatement ps) throws SQLException;
}
