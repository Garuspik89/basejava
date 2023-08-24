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
            size++;
        }
    }


    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Sorry, the resume doesn't exist");
            return null;
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Sorry, the resume doesn't exist");
            return;
        }
        storage[index] = storage[size - 1];
        storage[size] = null;
        size--;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("Sorry, the resume doesn't exist");
            return;
        }
        storage[index] = resume;


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
