package com.urise.storage;

import com.urise.exception.StorageException;
import com.urise.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    AbstractArrayStorageTest(Storage typeOfArrayStorage) {
        super(typeOfArrayStorage);
    }

    @Test(expected = StorageException.class)
    public void storageOverFlow() {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume("uuid" + i,"А" + i));
            } catch (StorageException exception) {
                Assert.fail("Sorry, storage is filled before the time");
            }
        }
        Resume resume = new Resume("uuid10000","А" + 10000);
        storage.save(resume);
    }
}