package com.urise.storage;
import com.urise.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

     public final int size() {
        return size;
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    public final void save(Resume resume) {
       int index = getIndex(resume.getUuid());
        if (size == STORAGE_LIMIT) {
            System.out.println("Sorry, storage is full, the resume can't be added");
        } else if (index >= 0) {
            System.out.println("Sorry, the resume  already exists");
        } else {
           saveResume(resume,index);
           size++;
        }
    }

    public final void clear(){
        Arrays.fill(storage,0,size,null);
        size = 0;
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Sorry, the resume doesn't exist");
            return;
        }
        deleteResume(index);
        storage[size] = null;
        size--;
    }

    public final Resume[] getAll() {
        return Arrays.copyOf(storage,size);
    }

    public final void update(Resume r){
        int index = getIndex(r.getUuid());
        if (index < 0) {
            System.out.println("Sorry, the resume doesn't exist");
            return;
        }
        storage[index] = r;
    }
    protected abstract int getIndex(String uuid);
    protected abstract void saveResume(Resume resume,int index);
    protected abstract void deleteResume(int index);


}

