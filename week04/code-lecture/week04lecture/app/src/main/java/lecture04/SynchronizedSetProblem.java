// Week 4
// raup@itu.dk * 12/09/2021
// raup@itu.dk * 15/09/2022

package lecture04;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SynchronizedSetProblem {

    public SynchronizedSetProblem() throws InterruptedException {

        for (int i = 0; i < 1_000_000; i++) {

            List<Integer> l = new ArrayList<Integer>();
            List<Integer> lSync = Collections.synchronizedList(l); // a program only accessing a thread-safe class does NOT imply that the program is thread-safe
                                                                   // It depends on the the definition of thread-safe program.
                                                                   //
                                                                   // If thread-safe programs are those without race conditions, this program is not thread-safe,
                                                                   // as the concurrent execution below illustrates
                                                                   //
                                                                   // If thread-safe programs are those without data races, the program is thread-safe,
                                                                   // as lSync is thread-safe and t1,t2 only execute methods calls on lSync

            Thread t1 = new Thread(() -> { add1IfAbsent(lSync); });
            Thread t2 = new Thread(() -> { add1IfAbsent(lSync); });

            t1.start();t2.start();
            t1.join();t2.join();

            if (!lSync.contains(2))
                System.out.println("2 was not inserted");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new SynchronizedSetProblem();
    }

    private void add1IfAbsent(List<Integer> l) {
        if (!l.contains(1))
            l.add(1);
        else
            l.add(2);
    }

}
