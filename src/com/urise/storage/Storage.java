package com.urise.storage;

import com.urise.model.Resume;

import java.util.List;

public interface Storage {

    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    Resume getByFullName(String fullName);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();
}
