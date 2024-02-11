package com.urise.storage;

import com.urise.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveResume(Resume resume, int index) {
        int reverseIndex = -(index) - 1;
        Resume[] tempArray = Arrays.copyOfRange(storage, reverseIndex, size);
        storage[reverseIndex] = resume;
        System.arraycopy(tempArray, 0, storage, reverseIndex + 1, tempArray.length);

    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }
}