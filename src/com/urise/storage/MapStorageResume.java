package com.urise.storage;

import com.urise.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageResume extends AbstractStorage {
    private final Map<String, Resume> MAP = new HashMap<>();

    @Override
    protected void doUpdate(Resume r, Object resume) {
        MAP.put(r.getUuid(), r);
    }

    @Override
    protected Resume getSearchKey(Object searchKey) {
        return MAP.get((String) searchKey);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected void doSave(Resume r, Object resume) {
        MAP.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void doDelete(Object resume) {
        MAP.remove(((Resume) resume).getUuid());
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
