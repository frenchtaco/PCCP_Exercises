// For week 7
// raup@itu.dk * 2023-10-20
package exercises07;

import java.util.concurrent.atomic.AtomicReference;

// Treiber's LockFree Stack (Goetz 15.4)
class LockFreeStack<T> {
    AtomicReference<Node<T>> top = new AtomicReference<Node<T>>(); // Initializes to null

    public void push(T value) {
        Node<T> newHead = new Node<T>(value);
        Node<T> oldHead;
        do {
            oldHead      = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead,newHead)); //------------ PUSH1
    }

    public T pop() {
        Node<T> newHead;
        Node<T> oldHead;
        do {
            oldHead = top.get(); // ---------------------------------- POP2
            if(oldHead == null) { return null; }
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead,newHead)); // ------------ POP3

        return oldHead.value;
    }

    public int size() { //for test purposes
        int count = 0;
        Node<T> curr = top.get();  
        while (curr != null) {
            count++;
            curr = curr.next;
        }
        return count;
    }

    // class for nodes
    private static class Node<T> {
        public final T value;
        public Node<T> next;

        public Node(T value) {
            this.value = value; 
            this.next  = null;
        }
    }
}
