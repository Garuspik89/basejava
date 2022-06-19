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
        int searchKey;
        searchKey = getSearchKey(resume.getUuid());
        if (searchKey != -1) {
            storage[searchKey] = resume;
            return;
        }
        System.out.println("ERROR: Resume is not found");
    }

    public void save(Resume r) {
        if (size + 1 > storage.length) {
            System.out.println("ERROR: Number of resume is more that resume storage");
            return;
        } else if ((getSearchKey(r.getUuid()) != -1)) {
            System.out.println("ERROR: Resume is found");
            return;
        }
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        int searchKey;
        searchKey = getSearchKey(uuid);
        if (searchKey == -1) {
            System.out.println("ERROR: Resume is not found");
            return null;
        }
        return storage[searchKey];
    }

    public void delete(String uuid) {
        int searchKey;
        Resume lastElementOfStorage;
        searchKey = getSearchKey(uuid);
        if (searchKey == -1) {
            System.out.println("ERROR: Resume is not found");
            return;
        }
        lastElementOfStorage = storage[size - 1];
        storage[size] = storage[searchKey];
        storage[size] = null;
        storage[searchKey] = lastElementOfStorage;
        size--;

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] storageWithoutNull = Arrays.copyOf(storage, size);
        return storageWithoutNull;
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
