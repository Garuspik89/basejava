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
    public List<Resume> getAllSorted() {

         Collection<Resume> values =  storage.values();
         List<Resume> newStorage = new ArrayList<Resume>(values);
         newStorage.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
         return newStorage;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
