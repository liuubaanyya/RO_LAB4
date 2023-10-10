import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {
    private Map<String, String> data = new HashMap<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    public String findPhoneNumber(String fullName) {
        readLock.lock();
        try {
            return data.get(fullName);
        } finally {
            readLock.unlock();
        }
    }

    public String findFullName(String phoneNumber) {
        readLock.lock();
        try {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                if (entry.getValue().equals(phoneNumber)) {
                    return entry.getKey();
                }
            }
            return null;
        } finally {
            readLock.unlock();
        }
    }

    public void addEntry(String fullName, String phoneNumber) {
        writeLock.lock();
        try {
            data.put(fullName, phoneNumber);
        } finally {
            writeLock.unlock();
        }
    }

    public void deleteEntry(String fullName) {
        writeLock.lock();
        try {
            data.remove(fullName);
        } finally {
            writeLock.unlock();
        }
    }
}
