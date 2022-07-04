package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {


    @Override
    void addResumeToList(Resume r, int index) {
        storage[index] = r;
    }

    @Override
    void removeResumeFromList(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected int getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}



