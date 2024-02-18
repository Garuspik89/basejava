package com.urise.storage;

import java.io.File;

public class FileStorageTest extends AbstractStorageTest {
        public FileStorageTest() {
        super(new FileStorage(new File("C://JavaOps//BaseJava//ListOfTestResume"), new ConverterOfFiles()));
    }
}

