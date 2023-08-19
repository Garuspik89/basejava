import javax.swing.*;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    int size = 0;
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0;  i < size; i++) {
            storage[i] = null;
            size = 0;
        }
    }


    void save(Resume r) {
        storage[size] = r;
        size = size + 1;
    }


    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
            int placeOfNullItem = 0;
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid == uuid) {
                    storage[i] = null;
                    placeOfNullItem = i;
                }
            }


        }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] storageWithoutNull = new Resume[size];
        for (int i = 0; i < size; i++) {
            storageWithoutNull[i] = storage[i];
        }
        return storageWithoutNull;
    }

    int size()
    {
        return size;
    }
}
