package com.urise.storage;

import com.urise.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    {
        arrayListStorage = new ArrayList<Resume>();
    }

    @Override
    protected int getIndexOfCollection(String uuid) {
        for (int i = 0; i < arrayListStorage.size(); i++) {
                if (arrayListStorage.get(i).getUuid().equals(uuid)) {
                    Resume resume = arrayListStorage.get(i);
                    return arrayListStorage.indexOf(resume);
            }
        }
        return -1;
    }

    @Override
    protected Resume returnResumeFromCollection(int index) {
        return arrayListStorage.get(index);
    }

    @Override
    protected int returnSizeOfCollection() {
        return arrayListStorage.size();
    }

    @Override
    protected void saveCollectionResume(Resume resume, int index) {
        arrayListStorage.add(resume);
    }

    @Override
    protected void clearCollection() {
        arrayListStorage.clear();
    }

    @Override
    protected Resume[] getAllCollectionElement() {
        Resume[] returnedResumes = arrayListStorage.toArray(new Resume[arrayListStorage.size()]);
        return returnedResumes;
    }

    @Override
    protected void deleteCollectionResume(int index, String uuid) {
        arrayListStorage.remove(index);
    }

    @Override
    protected void updateCollectionResume(Resume resume, int index) {
        arrayListStorage.set(index, resume);
    }


}

