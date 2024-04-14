package com.urise.storage;

import com.urise.exception.NotExistStorageException;
import com.urise.model.*;
import com.urise.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

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
        return sqlHelper.execute("" +
                        "SELECT * FROM resume r WHERE r.uuid=?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContactToResume(r);
                        addSectionToResume(r);
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name =? WHERE uuid = ?")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            deleteContacts(conn, r);
            deleteSections(conn, r);
            insertContacts(conn, r);
            insertSections(conn, r);
            return null;
        });
    }


    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
                ps.executeBatch();
            }
            insertContacts(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
                return null;
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume r" +
                "                         ORDER BY full_name", entry -> {

            ResultSet rs = entry.executeQuery();
            Map<String, Resume> mapForSearching = new LinkedHashMap<>();
            while (rs.next()) {
                String resumeUUid = rs.getString("uuid");
                Resume resume = mapForSearching.get(resumeUUid);
                if (resume == null) {
                    resume = new Resume(resumeUUid, rs.getString("full_name"));
                    mapForSearching.put(resumeUUid, resume);
                }

                addContactToResume(resume);
                addSectionToResume(resume);
            }
            return new ArrayList<>(mapForSearching.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(uuid) FROM resume", entry -> {
            ResultSet rs = entry.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                SectionType sectionType = SectionType.valueOf(String.valueOf(e.getKey()));
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE: {
                        TextSection data = (TextSection) e.getValue();
                        ps.setString(3, data.getData());
                        ps.addBatch();
                        break;
                    }
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        String str2 = "";
                        ListSection data = (ListSection) e.getValue();
                        for (String str : data.getData()) {
                            str2 = (str2.equals("")) ? str + "\n" : str2 + str + "\n";
                        }
                        ps.setString(3, str2);
                        ps.addBatch();
                        break;
                    }
                }
            }
            ps.executeBatch();
        }
    }

    private void addContactToResume(Resume resume) throws SQLException {
        sqlHelper.execute("select * from contact WHERE resume_uuid =?", ps -> {
            ps.setString(1, resume.getUuid());
            ResultSet rsContacts = ps.executeQuery();
            if (rsContacts == null) {
                return null;
            }
            while (rsContacts.next()) {
                ContactType type = ContactType.valueOf(rsContacts.getString("type"));
                String value = rsContacts.getString("value");
                resume.addContact(type, value);
            }
            return null;
        });


    }

    private void addSectionToResume(Resume resume) throws SQLException {
        sqlHelper.execute("select * from section WHERE resume_uuid =?", ps -> {
            ps.setString(1, resume.getUuid());
            ResultSet rsSection = ps.executeQuery();
            if (rsSection == null) {
                return null;
            }
            while (rsSection.next()) {
                SectionType sectionType = SectionType.valueOf(rsSection.getString("type"));
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE: {
                        resume.addSection(sectionType, new TextSection(rsSection.getString("value")));
                        break;
                    }
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        List<String> listOfListSection = new ArrayList<>();
                        String[] lines = rsSection.getString("value").split("\\n");
                        listOfListSection.addAll(Arrays.asList(lines));
                        resume.addSection(sectionType, new ListSection(listOfListSection));
                        break;
                    }
                }
            }
            return null;
        });
    }

    private void deleteContacts(Connection conn, Resume resume) {
        sqlHelper.execute("DELETE  FROM contact WHERE resume_uuid=?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }

    private void deleteSections(Connection conn, Resume resume) {
        sqlHelper.execute("DELETE  FROM section WHERE resume_uuid=?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }
}

