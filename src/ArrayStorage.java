
/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int index = 0; index < storage.length;index++) {
            storage[index] = null;
        }
    }

    void save(Resume r) {
        for (int index = 0; index < storage.length;index++) {
            if (storage[index] == null) {
                Resume resume = new Resume();
                resume.uuid = String.valueOf(r);
                storage[index] = resume;
                break;
            }
        }
    }

    Resume get(String uuid) {

        for (Resume resume : storage) {
            if ( resume != null && resume.uuid.equals(uuid)) {
                return resume;
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int index = 0; index < storage.length;index++) {
            if ( storage[index] != null && storage[index].uuid.equals(uuid)) {
                storage[index] = null;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int countOfResume = 0;
        int indexofResume = 0;
        for (Resume resume : storage) {
            if (resume != null) {
               countOfResume +=1;
            }
        }
        Resume[] storageWithoutNull = new Resume[countOfResume];
        for (Resume resume : storage) {
            if (resume != null) {
                storageWithoutNull[indexofResume] = resume;
                indexofResume +=1;
            }
        }
        return  storageWithoutNull;
    }

    int size() {
        int countOfResume = 0;
        for (Resume resume : storage) {
            if ( resume != null) {
                countOfResume += 1;
            }
        }
        return  countOfResume;
    }
}
