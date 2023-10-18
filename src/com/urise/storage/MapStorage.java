package com.urise.storage;

import com.urise.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    protected HashMap<String, Resume> storage = new HashMap<String, Resume>();

    @Override
    protected Object getSearchKey(Object searchKey) {
        return searchKey;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (storage.containsKey((String) searchKey));

    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> doGetList(){
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
