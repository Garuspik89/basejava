package com.urise.storage;

import com.Config;
import com.urise.storage.strategy.ConverterOfFiles;


public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(Config.get().getStorageDir(),new ConverterOfFiles()));
    }
}

