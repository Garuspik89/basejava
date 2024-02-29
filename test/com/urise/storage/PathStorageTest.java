package com.urise.storage;

import com.urise.storage.strategy.ConverterOfFiles;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage("C://JavaOps//BaseJava//ListOfTestResume", new ConverterOfFiles()));
    }
}
