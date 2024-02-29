package com.urise.storage;

import com.urise.storage.strategy.ConverterOfFiles;

import java.io.File;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(new File("C://JavaOps//BaseJava//ListOfTestResume"), new ConverterOfFiles()));
    }
}

