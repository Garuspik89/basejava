package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public final int size() {
        return size;
    }

    public final Resume get(String uuid) {
        int index = getSearchKey(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    public final  void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public final  void update(Resume resume) {
        int searchKey = getSearchKey(resume.getUuid());
        if (searchKey >= 0) {
            storage[searchKey] = resume;
            return;
        }
        System.out.println("ERROR: Resume is not found");

    }


    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public  final void save(Resume r) {
        int index = getSearchKey(r.getUuid());
        if (storage.length == size) {
            System.out.println("ERROR: Number of resume is more that resume storage");
        } else if ((index >= 0)) {
            System.out.println("ERROR: Resume is found");
        } else {
            addResumeToList(r, index);
            size++;
        }
    }

    public final  void delete(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey < 0) {
            System.out.println("ERROR: Resume is not found");
        } else {
            removeResumeFromList(searchKey);
            storage[size - 1] = null;
            size--;
        }
    }

    abstract void addResumeToList(Resume r, int index);

    abstract void removeResumeFromList(int searchKey);

    abstract protected int getSearchKey(String uuid);
}

