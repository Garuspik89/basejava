package com.urise.storage;

public class PathStorageTest extends AbstractStorageTest{
    public PathStorageTest() {
        super(new PathStorage("C://JavaOps//BaseJava//ListOfTestResume", new ConverterOfFiles()));
    }
}
