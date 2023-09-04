package com.urise.storage;

import com.urise.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected HashMap<String, Resume> mapStorage = new HashMap<String, Resume>();

    @Override
    protected Object getSearchKey(Object searchKey) {
        return searchKey;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        for (Map.Entry<String, Resume> set : mapStorage.entrySet()) {
            if (set.getKey().equals(searchKey)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapStorage.remove((String) searchKey);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        mapStorage.put((String) searchKey, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return mapStorage.get((String) searchKey);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        mapStorage.put((String) searchKey, resume);
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public Resume[] getAll() {
        return mapStorage.values().toArray(new Resume[mapStorage.values().size()]);
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}
