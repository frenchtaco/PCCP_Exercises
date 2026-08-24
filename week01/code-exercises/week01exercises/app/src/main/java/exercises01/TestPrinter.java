package exercises01;

import java.util.concurrent.locks.ReentrantLock;

public class TestPrinter {

    ReentrantLock l = new ReentrantLock();
    Printer p = new Printer();

    int counter = 10_000_000;

    // CREATE THREADS
    public TestPrinter() {
      
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < counter; i++) {
                p.print();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < counter; i++) {
                p.print();
            }
        });

        // START THREADS
        t1.start(); 
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Some thread was interrupted");
        }
    }

    


    public static void main(String[] args) {
        new TestPrinter();
        System.out.println("Hello World");
    }


    public class Printer {
        public void print() {
            l.lock();
            System.out.print(("-"));
            try { 
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
            System.out.print("|");
            l.unlock();
        }
    }
}