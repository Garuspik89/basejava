package com.urise.storage;

import com.urise.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size = 0;
    private Resume[] storage = new Resume[10000];

    public void clear() {
        Arrays.fill(storage,null);
        size = 0;
    }

    private int getResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) {
                return i;
            }
        }
            return -1;
    }

    public void save(Resume r) {
        int indexOfResume = getResume(r.getUuid());

        if (size == storage.length) {
            System.out.println("Sorry, storage is full, the resume can't be added");
            return;
        }

        if (indexOfResume != -1) {
            System.out.println("Sorry, the resume  already exists");
            return;
        }

        storage[size] = r;
        size = size + 1;
    }


    public Resume get(String uuid) {
        int indexOfResume = getResume(uuid);
        if (indexOfResume == -1) {
            System.out.println("Sorry, the resume doesn't exist");
            return null;
        } else {
            return storage[indexOfResume];
        }
    }

    public void delete(String uuid) {
        Resume resumeOfNull = null;
        int placeOfNullItem = getResume(uuid);
        if (placeOfNullItem == -1) {
            System.out.println("Sorry, the resume doesn't exist");
            return;
        }
        resumeOfNull = storage[placeOfNullItem];
        storage[placeOfNullItem] = storage[size - 1];
        storage[size] = resumeOfNull;
        storage[size] = null;
        size = size - 1;
    }

    public void update(Resume resume) {
        int indexOfResume = getResume(resume.getUuid());
        if (indexOfResume == -1) {
            System.out.println("Sorry, the resume doesn't exist");
            return;
        }
        storage[indexOfResume] = resume;


    }
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] storageWithoutNull  = Arrays.copyOf(storage,size);
        return storageWithoutNull;
    }

    public int size() {
        return size;
    }
}
