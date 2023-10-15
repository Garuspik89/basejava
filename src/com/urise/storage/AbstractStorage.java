package com.urise.storage;

import com.urise.exception.ExistStorageException;
import com.urise.exception.NotExistStorageException;
import com.urise.model.Resume;


public abstract class AbstractStorage implements Storage {

    public final void save(Resume resume) throws ExistStorageException {
        Object searchKey = getExistingSearchKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    public final Resume get(String uuid) throws NotExistStorageException {
        Object searchKey = getNotExistingSearchKey(uuid);
        return doGet(searchKey);

    }

    public final Resume getByFullName(String fullName) throws NotExistStorageException {
        Object searchKey = getNotExistingSearchKey(fullName);
        return doGet(searchKey);

    }

    public final void delete(String uuid) throws NotExistStorageException {
        Object searchKey = getNotExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    public final void update(Resume resume) throws NotExistStorageException {
        Object searchKey = getNotExistingSearchKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }


    private Object getExistingSearchKey(String uuid) {
        Object searchingKey = getSearchKey(uuid);
        if (isExist(searchingKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchingKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchingKey = getSearchKey(uuid);
        if (!isExist(searchingKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchingKey;
    }

    protected abstract Object getSearchKey(Object searchKey);

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doUpdate(Resume resume, Object searchKey);

}
