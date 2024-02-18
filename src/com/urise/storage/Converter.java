package com.urise.storage;

import com.urise.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Converter {

        void doWrite(Resume r, OutputStream outputStream) throws IOException;

        Resume doRead(InputStream inputStream) throws IOException;
}
