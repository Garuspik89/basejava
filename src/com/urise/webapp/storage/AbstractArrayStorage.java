package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
    /**
     * Array based storage for Resumes
     */
    public abstract class AbstractArrayStorage  {
        protected static final int STORAGE_LIMIT = 10000;

        protected Resume[] storage = new Resume[STORAGE_LIMIT];
        protected int size = 0;

        public int size() {
            return size;
        }

        public Resume get(String uuid) {
            int index = getSearchKey(uuid);
            if (index == -1) {
                System.out.println("Resume " + uuid + " not exist");
                return null;
            }
            return storage[index];
        }

        public abstract void clear();

        public abstract void update(Resume r);

        public abstract void save(Resume r);

        public abstract void delete(String uuid);

        public abstract Resume[] getAll();

        protected abstract int getSearchKey(String uuid);
    }

