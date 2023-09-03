package com.urise.storage;

import com.urise.exception.ExistStorageException;
import com.urise.exception.NotExistStorageException;
import com.urise.model.Resume;

import java.util.ArrayList;

public abstract class AbstractStorage implements Storage {
    protected Resume[] storage;
    protected ArrayList<Resume> arrayListStorage;

    public final int size() {
        return returnSizeOfCollection();
    }

    public final Resume get(String uuid) throws NotExistStorageException {
        int index = getIndexOfCollection(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return returnResumeFromCollection(index);
    }

    public final void save(Resume resume) throws ExistStorageException {
         int index = getIndexOfCollection(resume.getUuid());
         if (index >= 0) {
             throw new ExistStorageException(resume.getUuid());
         }
        saveCollectionResume(resume, index);
    }

    public final void clear() {
        clearCollection();
    }

    public final Resume[] getAll() {
        return getAllCollectionElement();
    }

    public final void delete(String uuid) throws NotExistStorageException {
        int index = getIndexOfCollection(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteCollectionResume(index, uuid);
    }

    public final void update(Resume resume) throws NotExistStorageException {
        int index = getIndexOfCollection(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        updateCollectionResume(resume, index);
    }

    protected abstract int getIndexOfCollection(String uuid);

    protected abstract Resume returnResumeFromCollection(int index);

    protected abstract int returnSizeOfCollection();

    protected abstract void saveCollectionResume(Resume resume, int index);

    protected abstract void clearCollection();

    protected abstract Resume[] getAllCollectionElement();

    protected abstract void deleteCollectionResume(int index, String uuid);

    protected abstract void updateCollectionResume(Resume resume, int index);
}
