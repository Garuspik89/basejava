package com.urise.storage;

import com.urise.storage.strategy.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage("C://JavaOps//BaseJava//ListOfTestResume", new JsonStreamSerializer()));
    }
}
