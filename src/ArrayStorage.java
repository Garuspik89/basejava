/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        for (int index = 0; index < size; index++) {
            storage[index] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size += 1;
    }

    Resume get(String uuid) {

        for (Resume resume : storage) {
            if (resume != null && resume.uuid.equals(uuid)) {
                return resume;
            }
        }
        return null;
    }

    void delete(String uuid) {
        int indexOfNull = -1;
        for (int index = 0; index < size; index++) {
            if (storage[index] != null && storage[index].uuid.equals(uuid)) {
                storage[index] = null;
                indexOfNull = index;
                size -= 1;
                break;
            }
        }
        if (indexOfNull != -1) {
            for (int index = indexOfNull; index < size; index++) {
                storage[index] = storage[index + 1];
                storage[index + 1] = null;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int indexofResume = 0;
        Resume[] storageWithoutNull = new Resume[size];
        for (Resume resume : storage) {
            if (resume == null) {
                break;
            } else {
                storageWithoutNull[indexofResume] = resume;
                indexofResume += 1;
            }
        }
        return storageWithoutNull;
    }

    int size() {
        return size;
    }
}
