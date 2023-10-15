package com.urise.storage;

import com.urise.model.Resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageResume  extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected void doUpdate(Resume r, Object resume) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume getSearchKey(Object searchKey) {
        return map.get((String)searchKey);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected void doSave(Resume r, Object resume) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void doDelete(Object resume) {
        map.remove(((Resume) resume).getUuid());
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        return null;
    }

    @Override
    public int size() {
        return map.size();
    }
}
