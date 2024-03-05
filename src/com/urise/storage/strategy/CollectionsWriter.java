package com.urise.storage.strategy;

import java.io.IOException;

@FunctionalInterface
public interface CollectionsWriter<T> {
    void  writeSomeCollection(T t) throws IOException;


}
