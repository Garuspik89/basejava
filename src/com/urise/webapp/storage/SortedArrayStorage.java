package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    @Override
    public void update(Resume resume) {
        int searchKey = getSearchKey(resume.getUuid());
        if (searchKey >= 0) {
            storage[searchKey] = resume;
            return;
        }
        System.out.println("ERROR: Resume is not found");

    }

    @Override
    public void save(Resume r) {
        int index = getSearchKey(r.getUuid());
        if (storage.length == size) {
            System.out.println("ERROR: Number of resume is more that resume storage");
        } else if ((index >= 0)) {
            System.out.println("ERROR: Resume is found");
        } else {
            int indexPut = -index - 1;
            System.arraycopy(storage, indexPut, storage, indexPut + 1, size - indexPut);
            storage[indexPut] = r;
            size++;
        }

    }

    @Override
    public void delete(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey < 0) {
            System.out.println("ERROR: Resume is not found");
            return;
        }
        int indexDelete = size - searchKey - 1;
        if (indexDelete > 0) {
            System.arraycopy(storage, searchKey + 1, storage, searchKey, indexDelete);
            storage[size -1] = null;
            size--;
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected int getSearchKey(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size,searchKey);
    }

}
