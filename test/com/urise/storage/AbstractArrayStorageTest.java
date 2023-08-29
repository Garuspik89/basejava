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
    private final Resume RESUME_1 = new Resume("UUID_1");
    private final Resume RESUME_2 = new Resume("UUID_2");
    private final Resume RESUME_3 = new Resume("UUID_3");
    private final Resume RESUME_4 = new Resume("UUID_4");

    AbstractArrayStorageTest(Storage typeOfArrayStorage) {
        this.storage = typeOfArrayStorage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        Resume[] resumesAfterClear = storage.getAll();
        Resume[] emptyResumes = new Resume[0];
        Assert.assertArrayEquals(resumesAfterClear, emptyResumes);
    }

    @Test
    public void delete() {
        storage.delete(RESUME_1.getUuid());
        assertSize(2);
        try {
            assertGet(RESUME_1);
        } catch (NotExistStorageException exception) {

        }
    }

    @Test
    public void getAll() {
        Resume[] actual = storage.getAll();
        Resume[] model = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(actual, model);
    }

    @Test
    public void update() {
        Resume resumeForUpdating = new Resume("UUID_1");
        storage.update(resumeForUpdating);
        Assert.assertEquals(resumeForUpdating, storage.get("UUID_1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void resumeAlreadyExists() {
        storage.save(RESUME_1);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    @Test(expected = StorageException.class)
    public void storageOverFlow() {
        storage.clear();
        int countOfUUID = 0;
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume("uuid" + countOfUUID));
                countOfUUID++;
            } catch (StorageException exception) {
                Assert.fail("Sorry, storage is filled before the time");
            }
        }
        Resume resume = new Resume("uuid" + countOfUUID);
        storage.save(resume);
    }
}