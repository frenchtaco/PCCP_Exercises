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

        assertEquals(stack.size(), noThreads);
        
    }


    @RepeatedTest(100)
    @DisplayName("Test sum of pop")
    public void testPop() throws Exception {

        threads = new Thread[noThreads];
        int[] pop_arr = new int[noThreads];

        for(int i = 0; i < noThreads; i++){
            final int value = 1;
            final int idx = i;
            threads[i] = new Thread(() -> {
                try {
                    
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
        assertEquals(sum, noThreads);
        
    }

    @RepeatedTest(100)
    @DisplayName("Test sum of pop")
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
        assertEquals(sum, noThreads);
        
    }
}