// raup@itu.dk * 2023-10-20
package exercises07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import java.util.concurrent.CyclicBarrier;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLockFreeStack {
    LockFreeStack<Integer> stack;
    CyclicBarrier barrier;
    int noThreads;
    Thread[] threads;

    @BeforeEach
    public void initialize() {
        noThreads = 1000; 
        barrier = new CyclicBarrier(noThreads);
        stack = new LockFreeStack<Integer>();
    }

	private static int expectedSum(int n) {
        return n * (n + 1) / 2;
    }

	private int stackSum() {
        int sum = 0;
        Integer v;
        while ((v = stack.pop()) != null) sum += v;
        return sum;
    }

    @RepeatedTest(100)
    @DisplayName("Test sum of push")
    public void testPush() throws Exception {

        threads = new Thread[noThreads];
        for(int i = 0; i < noThreads; i++){
            final int value = 1;
            threads[i] = new Thread(() -> {
                try {
                    barrier.await();
                    stack.push(value);

                } catch (Exception e) { System.out.println(e); }
                
            });
            threads[i].start();
        }

        for(Thread t: threads){
            t.join();
        }

        assertEquals(noThreads, stack.size());
        assertEquals(noThreads, stackSum());
    }


    @RepeatedTest(100)
    @DisplayName("Test sum of pop")
    public void testPop() throws Exception {

        threads = new Thread[noThreads];
        int[] pop_arr = new int[noThreads];

        for(int i = 0; i < noThreads; i++) {
            stack.push(i + 1);
        }

        for(int i = 0; i < noThreads; i++){
            final int idx = i;
            threads[i] = new Thread(() -> {
                try {
                    barrier.await();
                    pop_arr[idx] = stack.pop();
                } catch (Exception e) { System.out.println(e); }
                
            });
            threads[i].start();
        }

        for(Thread t: threads){
            t.join();
        }

        int sum = 0;
        for(int i = 0; i < noThreads; i++) {
            sum += pop_arr[i];
        }
        
        
        assertEquals(noThreads * (noThreads + 1) / 2, sum);
        assertEquals(0, stack.size());
        
    }

    @RepeatedTest(100)
    @DisplayName("Test empty pop")
    public void testEmptyPop() throws Exception {

        threads = new Thread[noThreads];
        int[] pop_arr = new int[noThreads];

        for(int i = 0; i < noThreads; i++){
            final int value = 1;
            final int idx = i;
            threads[i] = new Thread(() -> {
                try {
                    stack.pop();
                    barrier.await();
                    stack.push(value);
                    barrier.await();
                    pop_arr[idx] = stack.pop();
                } catch (Exception e) { System.out.println(e); }
                
            });
            threads[i].start();
        }

        for(Thread t: threads){
            t.join();
        }

        int sum = 0;
        for(int i = 0; i < noThreads; i++) {
            sum += pop_arr[i];
        }
        assertEquals(noThreads, sum);
        
    }
}