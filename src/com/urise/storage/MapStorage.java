package com.urise.storage;

import com.urise.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage<String> {

    protected HashMap<String, Resume> storage = new HashMap<String, Resume>();

    @Override
    protected String getSearchKey(String searchKey) {
        return searchKey;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return (storage.containsKey(searchKey));

    }

    @Override
    protected void doDelete(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void doSave(Resume resume, String searchKey) {
        storage.put(searchKey, resume);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doUpdate(Resume resume, String searchKey) {
        storage.put(searchKey, resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> doGetList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
