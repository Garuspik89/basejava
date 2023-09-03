package com.urise.storage;

import com.urise.exception.StorageException;
import com.urise.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected int size = 0;

    {
        storage = new Resume[STORAGE_LIMIT];
    }


    public final int size() {
        return size;
    }

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveResume(resume, (int) searchKey);
        size++;
    }

    @Override
    protected void doDelete(Object searchKey) {
        deleteResume((int) searchKey);
        storage[size] = null;
        size--;
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected Object getSearchKey(Object searchKey) {
        return getIndex((String) searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        if ((int) searchKey < 0) {
            return false;
        }
        return true;
    }

    abstract protected int getIndex(String uuid);

    abstract protected void saveResume(Resume resume, int index);

    abstract protected void deleteResume(int index);
}

