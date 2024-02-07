package com.urise.storage;


import com.urise.exception.StorageException;
import com.urise.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] root = directory.listFiles();

        for (File file : root) {
            doDelete(file);
        }
    }

    @Override
    public int size() {

        File[] files = directory.listFiles();
        return files.length;
    }


    @Override
    protected File getSearchKey(String searchKey) {
        return new File(directory, searchKey);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doDelete(File searchKey) {
        if (!searchKey.delete()) {
            throw new StorageException("Couldn't delete resume", searchKey.getName());
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName());
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("Couldn't read resume", file.getName());
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName());
        }
    }

    @Override
    protected List<Resume> doGetList() {
        File[] files = directory.listFiles();
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(doGet(file));
        }
        return list;
    }


    public abstract void doWrite(Resume resume, File file) throws IOException;

    public abstract Resume doRead(File file) throws IOException;

    public static void showAllFileInDirectory(File directory) {
        File[] root = directory.listFiles();

        if (root != null) {
            for (File next : root) {
                if (next.isFile()) {
                    System.out.println("Next file is : " + next.getName());
                } else if (next.isDirectory()) {
                    showAllFileInDirectory(next);
                }
            }
        }
    }
}
