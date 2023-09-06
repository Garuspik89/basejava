package com.urise.storage;

import com.urise.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected ArrayList<Resume> arrayListStorage = new ArrayList<Resume>();

    @Override
    public void clear() {
        arrayListStorage.clear();
    }

    @Override
    public Resume[] getAll() {
        return arrayListStorage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return arrayListStorage.size();
    }

    @Override
    protected Object getSearchKey(Object searchKey) {
        for (int i = 0; i < arrayListStorage.size(); i++) {
            if (arrayListStorage.get(i).getUuid().equals(searchKey)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
       return ((int) searchKey >= 0);
    }

    @Override
    protected void doDelete(Object searchKey) {
        arrayListStorage.remove((int) searchKey);

    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        arrayListStorage.add(resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
       return arrayListStorage.get((int) searchKey);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        arrayListStorage.set((int) searchKey, resume);
    }
}





