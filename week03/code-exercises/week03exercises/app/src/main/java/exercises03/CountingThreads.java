// For week 3 
// raup@itu.dk * 2025-09-03

package exercises03;

import java.util.concurrent.locks.ReentrantLock;

public class CountingThreads {
  volatile int count;
  ReentrantLock l = new ReentrantLock();
  public CountingThreads() throws InterruptedException {
    count = 0;

    CountingThread t1 = new CountingThread();
    CountingThread t2 = new CountingThread();

    t1.start();
    t2.start();

    t1.join();
    t2.join();

    System.out.println("count="+count);
  }

  public class CountingThread extends Thread {
    public void run() {
      //l.lock();
      int temp = count;
      try{
        Thread.sleep(3000);
      } catch (Exception e) {
        System.out.println(e);
     }

      count = temp + 1;
      //l.unlock();
    }
  }

  // gradle -PmainClass=exercises01.
  public static void main(String[] args) throws InterruptedException {
    new CountingThreads();
  }
}
