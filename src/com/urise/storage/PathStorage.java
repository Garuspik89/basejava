package com.urise.storage;

import com.urise.exception.StorageException;
import com.urise.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;

    private Converter converterOfFiles;

    protected PathStorage(String directory, Converter converterOfFiles) {

        this.converterOfFiles = converterOfFiles;
        this.directory = Paths.get(directory);
        if (!Files.isDirectory(this.directory) || !Files.isWritable(this.directory)) {
            throw new IllegalArgumentException(directory + " can't do");
        }
    }

    @Override
    protected Path getSearchKey(String searchKey) {
        return directory.resolve(searchKey);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.isRegularFile(searchKey);
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.toString());
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
           // Files.createFile(path);
            converterOfFiles.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Couldn't create path " + path, path.toString());
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return converterOfFiles.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.toString());
        }
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            converterOfFiles.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Resume update error", resume.getUuid());
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        try {
            Stream<Path> list = Files.list(directory);
            return list.map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error", e.toString());
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", "");
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", e.toString());
        }
    }
}
