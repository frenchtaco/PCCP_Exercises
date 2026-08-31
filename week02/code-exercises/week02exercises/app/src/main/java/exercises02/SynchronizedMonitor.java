package exercises02;

public class SynchronizedMonitor {
    private int readers = 0;
    private boolean writer = false;

    public synchronized void readLock() {
        while (writer) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        readers++;
    }

    public synchronized void readUnlock() {
        readers--;
        if (readers == 0) {
            notifyAll();
        }
    }

    public synchronized void writeLock() {
        try {
            while (writer) {
                wait();
                
            }
            writer = true;
            while (readers > 0) {
                wait();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void writeUnlock() {
        writer = false;
        notifyAll();
    }

    public void finalCount() {
        System.out.println(readers);
    }
}