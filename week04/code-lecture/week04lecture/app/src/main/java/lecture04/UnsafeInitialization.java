// Week 4
// raup@itu.dk * 2025-09-14
package lecture04;

import java.util.Objects;

class TestUnsafeInitialization {

    UnsafeInitialization u;

    // This code illustrates an issue with safe publication. Although,
    // in most systems and hardware, the issue with safe publication
    // is not observable when running the program.
    public TestUnsafeInitialization() throws InterruptedException {

        int N = 10_000_000;
        for (int i = 0; i < N; i++) {
            Thread t1 = new Thread(() -> {
                    // At this point u == null, either due to
                    // initiliazation to default value or because the
                    // write in line 44 happens-before this write (by
                    // the thread termination and thread start rules)
                    u = new UnsafeInitialization();
            });
            Thread t2 = new Thread(() -> {
                    // The `u` reads below and the write in line 24
                    // are not ordered by happens-before
                    if (!Objects.isNull(u) && u.readX()!=42)     
                        System.out.println("x is not equal 42");
            });
            Thread t3 = new Thread(() -> {
                    // The `u` reads below and the write in line 24
                    // are not ordered by happens-before
                    if (!Objects.isNull(u) && Objects.isNull(u.readO())) 
                        System.out.println("o is null");
            });
            t1.start();
            t2.start();
            t3.start();
            t1.join();
            t2.join();
            t3.join();
            u=null;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new TestUnsafeInitialization();
    }
}


class UnsafeInitialization {
    private int x;
    private int[] a;
    private Object o;

    public UnsafeInitialization() {
        this.x = 42;        
        this.a = new int[500000];
        this.o = new Object();
    }

    public int readX() {
        return this.x;
    }

    public Object readO() {
        return this.o;
    }
}
