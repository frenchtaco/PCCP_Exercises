package exercises04;

public class CustomBarrier {

    private final int maxCount;
    private int count = 0;

    private final CustomSemaphore mutex = new CustomSemaphore(1);
    private final CustomSemaphore turnstile = new CustomSemaphore(0); // *turnstile* as once opened, it lets every thread through one at a time

    public CustomBarrier(int maxCount) {
        this.maxCount = maxCount;
    }

    public void await() throws InterruptedException {
        mutex.acquire();
        count++; // Critical section
        mutex.release();

        if (count == maxCount) {
            turnstile.release();
        }

        turnstile.acquire();       
        turnstile.release();
    }
}