package com.urise.storage;

import com.urise.exception.NotExistStorageException;
import com.urise.model.Resume;
import com.urise.sql.SqlExecutor;
import com.urise.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    SqlHelper sqlHelper;

    @Override
    public void clear() {
        sqlHelper = new SqlHelper();
        sqlHelper.preparedStatement("DELETE FROM resume");
        PreparedStatement ps = sqlHelper.getPs();
        executeQuery(ps, entry -> ps.execute());
    }

    @Override
    public Resume get(String uuid) {
        sqlHelper = new SqlHelper();
        final String[] fullName = new String[1];
        sqlHelper.preparedStatement("SELECT * FROM resume r WHERE r.uuid =?");
        PreparedStatement ps = sqlHelper.getPs();
        executeQuery(ps, entry -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            fullName[0] = rs.getString("full_name");
        });

        return new Resume(uuid, fullName[0]);
    }

    @Override
    public void update(Resume r) {
        sqlHelper = new SqlHelper();
        sqlHelper.preparedStatement("UPDATE resume SET full_name = 'Igor' WHERE uuid = 'UUID_1'");
        PreparedStatement ps = sqlHelper.getPs();
        executeQuery(ps, entry -> ps.execute());
    }

    @Override
    public void save(Resume r) {
        sqlHelper = new SqlHelper();
        sqlHelper.preparedStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)");
        PreparedStatement ps = sqlHelper.getPs();
        executeQuery(ps, entry -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper = new SqlHelper();
        sqlHelper.preparedStatement("DELETE FROM resume WHERE uuid = ?");
        PreparedStatement ps = sqlHelper.getPs();
        executeQuery(ps, entry -> {
            ps.setString(1, uuid);
            ps.execute();
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        sqlHelper = new SqlHelper();
        sqlHelper.preparedStatement("SELECT * FROM resume ORDER BY full_name");
        PreparedStatement ps = sqlHelper.getPs();
        final List<Resume> list = new ArrayList<>();
        executeQuery(ps, entry -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                list.add(resume);
            }
        });
        return list;
    }

    @Override
    public int size() {
        sqlHelper = new SqlHelper();
        sqlHelper.preparedStatement("SELECT COUNT(uuid) FROM resume");
        PreparedStatement ps = sqlHelper.getPs();
        final int[] size = new int[1];
        executeQuery(ps, entry -> {
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException("");
            }
            size[0] = rs.getInt("count");
        });
        return size[0];
    }

    private void executeQuery(PreparedStatement ps, SqlExecutor sqlExecutor) {
        try {
            sqlExecutor.execute(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

