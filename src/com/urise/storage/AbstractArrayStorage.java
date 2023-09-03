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


    @Override
    protected Resume returnResumeFromCollection(int index) {
        return storage[index];
    }

    @Override
    protected void saveCollectionResume(Resume resume, int index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            saveResume(resume, index);
            size++;
        }
    }

    @Override
    protected void clearCollection() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected Resume[] getAllCollectionElement() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected void deleteCollectionResume(int index, String uuid) {
        deleteResume(index);
        storage[size] = null;
        size--;
    }

    @Override
    protected int returnSizeOfCollection() {
        return size;
    }

    @Override
    protected void updateCollectionResume(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected int getIndexOfCollection(String uuid) {
        return getIndex(uuid);
    }


    protected abstract int getIndex(String uuid);

    protected abstract void deleteResume(int index);

    protected abstract void saveResume(Resume resume, int index);

}

