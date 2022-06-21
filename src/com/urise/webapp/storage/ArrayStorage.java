package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void update(Resume resume) {
        int searchKey = getSearchKey(resume.getUuid());
        if (searchKey != -1) {
            storage[searchKey] = resume;
            return;
        }
        System.out.println("ERROR: Resume is not found");
    }

    public void save(Resume r) {
        if (storage.length == size) {
            System.out.println("ERROR: Number of resume is more that resume storage");
        } else if ((getSearchKey(r.getUuid()) != -1)) {
            System.out.println("ERROR: Resume is found");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey == -1) {
            System.out.println("ERROR: Resume is not found");
            return null;
        }
        return storage[searchKey];
    }

    public void delete(String uuid) {
        int searchKey = getSearchKey(uuid);
        Resume lastElementOfStorage;
        if (searchKey == -1) {
            System.out.println("ERROR: Resume is not found");
            return;
        }

        storage[searchKey] = null;
        storage[searchKey] = storage[size - 1];
        size--;

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
