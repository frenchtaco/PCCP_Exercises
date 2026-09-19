package exercises04;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CustomSemaphore {
    private int permits;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition permitAvailable = lock.newCondition();

    public CustomSemaphore(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("permits must be >= 0");
        }
        this.permits = capacity;
    }

    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permits == 0) {
                permitAvailable.await();
            }
            permits--;
        } finally {
            lock.unlock();
        }
    }

    public void release() throws InterruptedException {
        lock.lock();
        try {
            permits++;
            permitAvailable.signal();
        } finally {
            lock.unlock();
        }
    }
}
