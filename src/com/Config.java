package com;

import com.urise.storage.SqlStorage;
import com.urise.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Config INSTANCE = new Config();
    private static File PROPS;
    private Properties props = new Properties();
    private File storageDir;
    private Storage storage;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        PROPS = new File("config\\resumes.properties");
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Illegal config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }

}
