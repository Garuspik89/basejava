package com.urise.exception;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(Exception e, String uuid) {
        this(e.getMessage(),uuid);
    }

    public StorageException(Exception e) {
        this(e,"");
    }

    public String getUuid() {
        return uuid;
    }
}
