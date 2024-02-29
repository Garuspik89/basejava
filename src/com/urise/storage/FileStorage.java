package com.urise.storage;

import com.urise.exception.StorageException;
import com.urise.model.Resume;
import com.urise.storage.strategy.Converter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final Converter converterOfFiles;

    protected FileStorage(File directory, Converter converterOfFiles) {


        this.converterOfFiles = converterOfFiles;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " can't do");
        }
        this.directory = directory;
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
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Couldn't delete resume", file.getName());
        }

    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName());
        }
        doUpdate(resume, file);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return converterOfFiles.doRead(new BufferedInputStream((new FileInputStream(file))));
        } catch (IOException e) {
            throw new StorageException("Couldn't read resume", file.getName());
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            converterOfFiles.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName());
        }

    }

    @Override
    protected List<Resume> doGetAll() {
        File[] files = getListFiles();
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    public void clear() {
        File[] files = getListFiles();
        for (File file : files) {
            doDelete(file);
        }

    }

    @Override
    public int size() {
        return getListFiles().length;
    }

    private File[] getListFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory isn't exist", directory.getName());
        }
        return files;
    }
}
