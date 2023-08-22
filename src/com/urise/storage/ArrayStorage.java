package com.urise.storage;

import com.urise.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size = 0;
    private final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];

    public void clear() {
        Arrays.fill(storage,null);
        size = 0;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
            return -1;
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());

        if (size == storage.length) {
            System.out.println("Sorry, storage is full, the resume can't be added");
        } else if (index != -1) {
            System.out.println("Sorry, the resume  already exists");
        } else {
            storage[size] = r;
            size = size + 1;
        }
    }


    public Resume get(String uuid) {
        int indexOfResume = findIndex(uuid);
        if (indexOfResume == -1) {
            System.out.println("Sorry, the resume doesn't exist");
            return null;
        } else {
            return storage[indexOfResume];
        }
    }

    public void delete(String uuid) {
        int placeOfNullItem = findIndex(uuid);
        if (placeOfNullItem == -1) {
            System.out.println("Sorry, the resume doesn't exist");
            return;
        }
        storage[placeOfNullItem] = storage[size - 1];
        storage[size] = null;
        size = size - 1;
    }

    public void update(Resume resume) {
        int indexOfResume = findIndex(resume.getUuid());
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
        return Arrays.copyOf(storage,size);
    }

    public int size() {
        return size;
    }
}
