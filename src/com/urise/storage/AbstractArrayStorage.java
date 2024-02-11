package com.urise.storage;

import com.urise.exception.StorageException;
import com.urise.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    public final int size() {
        return size;
    }

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveResume(resume, searchKey);
        size++;
    }

    @Override
    protected void doDelete(Integer searchKey) {
        deleteResume(searchKey);
        storage[size] = null;
        size--;
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected Integer getSearchKey(String searchKey) {
        return getIndex(searchKey);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    abstract protected int getIndex(String uuid);

    abstract protected void saveResume(Resume resume, int index);

    abstract protected void deleteResume(int index);
}

