package com.urise.storage.strategy;

import java.io.IOException;
import java.util.Map;

@FunctionalInterface
public interface CollectionsWriter<K,V> {
    void  writeSomeCollection(Map.Entry<K,V> entry) throws IOException;


}
