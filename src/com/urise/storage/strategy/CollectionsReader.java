package com.urise.storage.strategy;

import java.io.IOException;

@FunctionalInterface
public interface CollectionsReader {
    void readSomeCollection() throws IOException;
}

