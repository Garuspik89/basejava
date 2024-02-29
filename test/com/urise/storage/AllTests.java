package com.urise.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ArrayStorageTest.class,
                SortedArrayStorageTest.class,
                ListStorageTest.class,
                MapResumeStorageTest.class,
                MapStorageTest.class,
                FileStorageTest.class,
                PathStorageTest.class,
                JsonPathStorageTest.class,
                XmlPathStorageTest.class,
                DataStreamSerializerTest.class

        })
public class AllTests {
}
