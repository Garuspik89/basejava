package com.urise.storage;

import com.urise.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    {
        arrayListStorage = new ArrayList();
    }

    @Override
    public void clear() {
        arrayListStorage.clear();
    }

    @Override
    public Resume[] getAll() {
         Resume[] convertedArray = arrayListStorage.toArray(new Resume[arrayListStorage.size()]);
         return convertedArray;
    }

    @Override
    public int size() {
        return arrayListStorage.size();
    }

    @Override
    protected Object getSearchKey(Object searchKey) {
        for (int i = 0; i < arrayListStorage.size(); i++) {
            if (arrayListStorage.get(i).getUuid().equals((String) searchKey)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        if ((int) searchKey  < 0) {
            return false;
        }
        return true;
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





