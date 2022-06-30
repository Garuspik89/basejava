package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    void addResumeToList(Resume r, int index) {
        int indexPut = -index - 1;
        System.arraycopy(storage, indexPut, storage, indexPut + 1, size - indexPut);
        storage[indexPut] = r;
    }

    @Override
    void removeResumeFromList(int searchKey) {
        int indexDelete = size - searchKey - 1;
        System.arraycopy(storage, searchKey + 1, storage, searchKey, indexDelete);
    }

    @Override
    protected int getSearchKey(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

}

