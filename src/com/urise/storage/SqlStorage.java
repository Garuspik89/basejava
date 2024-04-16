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
            }
            addContactsToResumes(mapForSearching);
            addSectionsToResumes(mapForSearching);
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
                        ListSection data = (ListSection) e.getValue();
                        ps.setString(3, String.join("\n", data.getData()));
                        ps.addBatch();
                        break;
                    }
                }
            }
            ps.executeBatch();
        }
    }

    private void addContactToResume(Resume resume) throws SQLException {
        sqlHelper.execute("SELECT * FROM contact WHERE resume_uuid =?", ps -> {
            ps.setString(1, resume.getUuid());
            ResultSet rsContacts = ps.executeQuery();
            while (rsContacts.next()) {
                doContactToResume(rsContacts, resume);
            }
            return null;
        });
    }

    private void addContactsToResumes(Map<String, Resume> map) throws SQLException {
        sqlHelper.execute("SELECT * FROM contact ", ps -> {
            ResultSet rsContacts = ps.executeQuery();
            while (rsContacts.next()) {
                Resume resume = map.get(rsContacts.getString("resume_uuid"));
                doContactToResume(rsContacts, resume);
            }
            return null;
        });
    }

    private void doContactToResume(ResultSet rs, Resume resume) throws SQLException {
        if (rs == null) {
            return;
        }
        while (rs.next()) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            String value = rs.getString("value");
            resume.addContact(type, value);
        }
    }

    private void addSectionToResume(Resume resume) throws SQLException {
        sqlHelper.execute("SELECT * FROM section WHERE resume_uuid =?", ps -> {
            ps.setString(1, resume.getUuid());
            ResultSet rsSection = ps.executeQuery();
            if (rsSection == null) {
                return null;
            }
            while (rsSection.next()) {
                doSectionToResume(rsSection, resume);
            }
            return null;
        });
    }

    private void addSectionsToResumes(Map<String, Resume> map) throws SQLException {
        sqlHelper.execute("SELECT * FROM section ", ps -> {
            ResultSet rsSection = ps.executeQuery();
            if (rsSection == null) {
                return null;
            }
            while (rsSection.next()) {
                Resume resume = map.get(rsSection.getString("resume_uuid"));
                doSectionToResume(rsSection, resume);
            }
            return null;
        });
    }

    private void doSectionToResume(ResultSet rs, Resume resume) throws SQLException {
        SectionType sectionType = SectionType.valueOf(rs.getString("type"));
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE: {
                resume.addSection(sectionType, new TextSection(rs.getString("value")));
                break;
            }
            case ACHIEVEMENT:
            case QUALIFICATIONS: {
                List<String> listOfListSection = new ArrayList<>();
                String[] lines = rs.getString("value").split("\\n");
                listOfListSection.addAll(Arrays.asList(lines));
                resume.addSection(sectionType, new ListSection(listOfListSection));
                break;
            }
        }
    }

    private void deleteContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void deleteSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }
}

