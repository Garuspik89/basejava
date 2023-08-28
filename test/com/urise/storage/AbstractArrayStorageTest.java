package com.urise.storage;

import com.urise.exception.ExistStorageException;
import com.urise.exception.NotExistStorageException;
import com.urise.exception.StorageException;
import com.urise.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public abstract class AbstractArrayStorageTest {
    private Storage storage;

    public AbstractArrayStorageTest() {

    }

    AbstractArrayStorageTest(Storage sortedArrayStorageTest) {
        this.storage = sortedArrayStorageTest;
    }


    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void get() {
        Resume resume = storage.get("uuid1");
        Assert.assertEquals(resume, storage.get("uuid1"));
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        Assert.assertEquals(resume, storage.get("uuid4"));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void delete() {
        storage.delete("uuid2");
        Assert.assertNull(storage.get("uuid2"));
    }

    @Test
    public void getAll() {
        Resume[] secondStorage = storage.getAll();
        Assert.assertArrayEquals(secondStorage, storage.getAll());
    }

    @Test
    public void update() {
        Resume resume = storage.get("uuid1");
        resume.setUuid("uuid16");
        Assert.assertEquals(resume, storage.get("uuid16"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
        throw new NotExistStorageException("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void resumeAlreadyExists() throws Exception {
        storage.get(UUID_1);
        throw new ExistStorageException(UUID_1);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = StorageException.class)
    public void storageOverFlow() {
        int countOfUUID = 4;
        for (int i = 3; i <= 9999; i++) {
            try {
                storage.save(new Resume("uuid" + countOfUUID));
                countOfUUID++;
            } catch (StorageException exception) {
                Assert.fail("Sorry, storage is filled before the time");
            }
        }
            Resume resume = new Resume("uuid" + countOfUUID);
            storage.save(resume);
            throw new StorageException("Sorry, storage is full",resume.getUuid());
        }
    }