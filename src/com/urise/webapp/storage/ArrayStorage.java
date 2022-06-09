package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void update(Resume resume) {
        if (!isResumeExist(resume.getUuid())) {
            System.out.println("ERROR: Resume is not found");
            return;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(resume.getUuid())) {
                storage[i] = resume;
            }
        }
    }

    public void save(Resume r) {
        if (isResumeExist(r.getUuid())) {
            System.out.println("ERROR: Resume is found");
            return;
        }
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        if (!isResumeExist(uuid)) {
            System.out.println("ERROR: Resume is not found");
        }
        for (int i = 0; i < size; i++) {
            return storage[i];
        }
        return null;
    }

    public void delete(String uuid) {
        if (!isResumeExist(uuid)) {
            System.out.println("ERROR: Resume is not found");
        }
        int indexOfNull = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                indexOfNull = i;
                break;
            }
        }
        if (indexOfNull != -1) {
            for (int i = indexOfNull; i < size; i++) {
                storage[i] = storage[i + 1];
                indexOfNull += 1;
            }
            size--;
            storage[indexOfNull] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] storageWithoutNull = new Resume[size];
        for (int i = 0; i < size; i++) {
            storageWithoutNull[i] = storage[i];
        }
        return storageWithoutNull;
    }

    public int size() {
        return size;
    }

    private boolean isResumeExist(String uuid) {
        if (size == 0) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }
}
