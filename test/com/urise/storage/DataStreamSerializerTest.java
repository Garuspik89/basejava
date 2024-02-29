package com.urise.storage;


import com.urise.storage.strategy.DataStreamSerializer;

public class DataStreamSerializerTest extends AbstractStorageTest{
    public DataStreamSerializerTest() {
        super(new PathStorage("C://JavaOps//BaseJava//ListOfTestResume", new DataStreamSerializer()));
    }
}
