package com.urise.storage;

import com.urise.storage.strategy.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage("C://JavaOps//BaseJava//ListOfTestResume", new XmlStreamSerializer()));
    }
}
