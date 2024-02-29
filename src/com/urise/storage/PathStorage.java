package com.urise.storage;

import com.urise.exception.StorageException;
import com.urise.model.Resume;
import com.urise.storage.strategy.Converter;

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
    private final Path directory;

    private final Converter converterOfFiles;

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
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create path " + path, path.toString());
        }
        doUpdate(resume, path);
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
        return getStreamList(directory).map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getStreamList(directory).forEach(this::doDelete);
    }


    @Override
    public int size() {
        return (int) getStreamList(directory).count();
    }

    private Stream<Path> getStreamList(Path directory) {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory isn't exist", e.toString());
        }
    }
}
