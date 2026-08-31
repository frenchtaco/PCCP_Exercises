package exercises02;

public class TestMonitor {
    private int readers = 0;
    private boolean writer = false;

    public synchronized void readLock() {
        while (writer) {
            wait();
        }
    }

    public synchronized void readUnlock() {
        readers--;
        if (readers == 0) {
            notifyAll();
        }
    }

    public synchronized void writeLock() {
        while (readers > 0 || writer) {
            
        }
    }
}