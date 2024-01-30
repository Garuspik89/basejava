package com.urise.storage;

import com.urise.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageResume extends AbstractStorage<Resume> {
    private final Map<String, Resume> MAP = new HashMap<>();

    @Override
    protected void doUpdate(Resume r, Resume resume) {
        MAP.put(r.getUuid(), r);
    }

    @Override
    protected Resume getSearchKey(String searchKey) {
        return MAP.get(searchKey);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    protected void doSave(Resume r, Resume resume) {
        MAP.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected void doDelete(Resume resume) {
        MAP.remove(resume.getUuid());
    }

    @Override
    public void clear() {
        MAP.clear();
    }

    @Override
    protected List<Resume> doGetList() {
        return new ArrayList<>(MAP.values());
    }

    @Override
    public int size() {
        return MAP.size();
    }
}
