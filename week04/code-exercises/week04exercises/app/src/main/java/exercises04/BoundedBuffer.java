package exercises04;

import java.util.concurrent.Semaphore;

public class BoundedBuffer<T> implements BoundedBufferInterface<T> {
    private int takePtr = 0;
    private int insertPtr = 0;
    private T[] items;
    private Semaphore notEmpty;
    private Semaphore notFull;
    private Semaphore mutex;

    @SuppressWarnings("unchecked")
    public BoundedBuffer(int capacity) {
        items = (T[]) new Object[capacity];
        notEmpty = new Semaphore(0, true);
        notFull = new Semaphore(capacity, true);
        mutex = new Semaphore(1, true);
    }

    public T take() throws InterruptedException {
        notEmpty.acquire();
        mutex.acquire();
    
        T item;

        try {
            item = items[takePtr];
            items[takePtr] = null;
            takePtr = (takePtr + 1) % items.length;
        } finally { // Catch block is emitted intentionally as otherwise the variable item may not be initialized (compile error). It still propagates correctly.
            mutex.release();
        }

        notFull.release();
        return item;
        
    }

    public void insert(T elem) throws InterruptedException {
        notFull.acquire();
        mutex.acquire();

        try {
            items[insertPtr] = elem;
            insertPtr = (insertPtr + 1) % items.length;
        } finally {
            mutex.release();
        }

        notEmpty.release();
    }
    
}
