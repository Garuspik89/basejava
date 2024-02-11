package com.urise.storage;

import com.urise.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    protected ArrayList<Resume> arrayListStorage = new ArrayList<Resume>();

    @Override
    public void clear() {
        arrayListStorage.clear();
    }

    @Override
    public List<Resume> doGetAll() {
        return new ArrayList<Resume>(arrayListStorage);
    }

    @Override
    public int size() {
        return arrayListStorage.size();
    }

    @Override
    protected Integer getSearchKey(String searchKey) {
        for (int i = 0; i < arrayListStorage.size(); i++) {
            if (arrayListStorage.get(i).getUuid().equals(searchKey)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return (searchKey >= 0);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        arrayListStorage.remove(searchKey.intValue());

    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        arrayListStorage.add(resume);
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return arrayListStorage.get(searchKey);
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        arrayListStorage.set(searchKey, resume);
    }
}





