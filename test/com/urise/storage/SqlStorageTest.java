package com.urise.storage;

import com.Config;

public class SqlStorageTest  extends AbstractStorageTest{
    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getDbURL(), Config.get().getDbUser(), Config.get().getDbPassword()));
    }
}
