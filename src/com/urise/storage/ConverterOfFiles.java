package com.urise.storage;

import com.urise.exception.StorageException;
import com.urise.model.Resume;

import java.io.*;

public class ConverterOfFiles implements Converter{

    @Override
    public void doWrite(Resume r, OutputStream outputStream) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(r);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Can't read resume", e.getMessage());
        }
    }
}
