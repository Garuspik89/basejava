package com.urise.storage;

import com.urise.exception.ExistStorageException;
import com.urise.exception.NotExistStorageException;
import com.urise.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractStorageTest {

    protected final Storage storage;

    public AbstractStorageTest(Storage typeOfArrayStorage) {
        this.storage = typeOfArrayStorage;
    }

    private final String UUID_1 = "UUID_1";
    private final String UUID_2 = "UUID_2";
    private final String UUID_3 = "UUID_3";
    private final String UUID_4 = "UUID_4";
    private final String DUMMY = "dummy";
    private final Resume RESUME_1 = new Resume(UUID_1);
    private final Resume RESUME_2 = new Resume(UUID_2);
    private final Resume RESUME_3 = new Resume(UUID_3);
    private final Resume RESUME_4 = new Resume(UUID_4);

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }


    @Test
    public void update() {
        Resume resumeForUpdating = new Resume(UUID_1);
        storage.update(resumeForUpdating);
        Assert.assertEquals(resumeForUpdating, storage.get(UUID_1));
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
    public void getAll() {
        Resume[] actual = storage.getAll();
        Resume[] model = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(actual, model);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(RESUME_1.getUuid());
        assertSize(2);
        try {
            assertGet(RESUME_1);
        } catch (NotExistStorageException exception) {
            throw new NotExistStorageException("Sorry, resume doesn't exist");
        }
        Assert.fail();
    }


    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test(expected = ExistStorageException.class)
    public void resumeAlreadyExists() {
        storage.save(RESUME_1);
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
}
