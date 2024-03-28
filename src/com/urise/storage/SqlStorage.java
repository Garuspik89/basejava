package com.urise.storage;

import com.urise.exception.ExistStorageException;
import com.urise.exception.NotExistStorageException;
import com.urise.model.Resume;
import com.urise.sql.SqlHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", entry -> {
            entry.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return (Resume) sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid =?", entry -> {
            entry.setString(1, uuid);
            ResultSet rs = entry.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.execute("UPDATE resume SET full_name =? WHERE uuid = ?", entry -> {
            entry.setString(1, r.getFullName());
            entry.setString(2, r.getUuid());
            if (entry.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", entry -> {
            entry.setString(1, r.getUuid());
            entry.setString(2, r.getFullName());
            if (entry.executeUpdate() == 0) {
                throw new ExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", entry -> {
            entry.setString(1, uuid);
            if (entry.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return (List<Resume>) sqlHelper.execute("SELECT * FROM resume ORDER BY full_name", entry -> {
            List<Resume> list = new ArrayList<>();
            ResultSet rs = entry.executeQuery();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;
        });
    }

    @Override
    public int size() {
        return (int) sqlHelper.execute("SELECT COUNT(uuid) FROM resume", entry -> {
            ResultSet rs = entry.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }
}

