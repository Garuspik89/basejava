package com.urise.storage;

import com.urise.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    protected int getIndex(String uuid) {
        Resume resumeForSearching = new Resume();
        resumeForSearching.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, resumeForSearching);
    }

    @Override
    protected void saveResume(Resume resume, int index) {
        int reverseIndex = -(index) - 1;
        Resume tempArray[] = Arrays.copyOfRange(storage,reverseIndex,size);
        storage[reverseIndex] = resume;
        System.arraycopy(tempArray,0,storage,reverseIndex + 1,tempArray.length);

    }
}