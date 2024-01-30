package com.urise.storage;

import com.urise.exception.ExistStorageException;
import com.urise.exception.NotExistStorageException;
import com.urise.model.Resume;

import java.util.*;


public abstract class AbstractStorage<E> implements Storage {

    private final Comparator<Resume> COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public final void save(Resume resume) throws ExistStorageException {
        E searchKey = getExistingSearchKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    public final Resume get(String uuid) throws NotExistStorageException {
        E searchKey = getNotExistingSearchKey(uuid);
        return doGet(searchKey);

    }

    public final void delete(String uuid) throws NotExistStorageException {
        E searchKey = getNotExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    public final void update(Resume resume) throws NotExistStorageException {
        E searchKey = getNotExistingSearchKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }


    private E getExistingSearchKey(String uuid) {
        E searchingKey = getSearchKey(uuid);
        if (isExist(searchingKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchingKey;
    }

    private E getNotExistingSearchKey(String uuid) {
        E searchingKey = getSearchKey(uuid);
        if (!isExist(searchingKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchingKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = doGetList();
        Collections.sort(sortedList, COMPARATOR);
        return sortedList;
    }

    protected abstract E getSearchKey(String searchKey);

    protected abstract boolean isExist(E searchKey);

    protected abstract void doDelete(E searchKey);

    protected abstract void doSave(Resume resume, E searchKey);

    protected abstract Resume doGet(E searchKey);

    protected abstract void doUpdate(Resume resume, E searchKey);

    protected abstract List<Resume> doGetList();

}
