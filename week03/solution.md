# 3.1.

## Files
CountingThreads.java

## Notes

### 1

An action represents an execution of an operatin inside a thread. These cover variable accesses and synchronization actions. 

count = 0 //variable access

t1, t2 = new CountingThread(); // write action
t1.start(), t2.start() // sync action

temp, count // write/read

t1.join(), t2.join() //sync action

### 2 + 3

Note: The complete happens-before set is the `transitive closure of the union of the happens-before sets` below.
HB_m = {
    m(init(count)),

    m(start(t1)),
    m(start(t2)),

    m(join(t1)),
    m(join(t2)),
}

HB_t1 = {
    m(init(temp = count)),
    m(set(count = temp + 1))
}

HB_t2 = {
    m(init(temp = count)),
    m(set(count = temp + 1))
}

HB_init = {
    m(start(t1)) -> m(init(temp = count)),
    m(start(t2)) -> m(init(temp = count)),
}

HB_ter = {
    m(set(count = temp + 1)) -> join(t1)
    m(set(count = temp + 1)) -> join(t2)
}

### 4

Let int temp = count; be (1), and count = temp + 1; be (2).

1) m(start(t1)), t1(1), t1(2), m(start(t2)) t2(1), t2(2)

2) m(start(t1)), m(start(t2)) t1(1), t2(1), t1(2), t2(2)

3) m(start(t2)), m(start(t1)) t2(1), t1(1), t2(2), t1(2)

4) m(start(t2)), t2(1), t2(2), m(start(t1)) t1(1), t1(2)

### 5

While the JMM (Java Memory Model) provides program order, there is no happens-before order, i.e. no sync order between the threads. Thus, there is no way of knowing if count will be 1 or 2 at the end of execution.

### 6

By using a delay to highlight the data race, and a lock, we have shown that a data race has been avoided. Hence, what we are ensuring now is: 

1) m(start(t1)), t1(1), t1(2), m(start(t2)) t2(1), t2(2)

### 7

We know that t1(1) -> t1(2) -> t2(1) -> t2(2)

### 8

The volatile keyword does not ensure mutual exclusion, and given that temp is instantiated inside the `CountingThread`class, it cannot be volatile. Regardless, since we cannot ensure mutual exclusion, we cannot avoid the data race.

# Exercise 3.2

## Files
TestStringSet.java

## Notes

### 1
The memory model does not ensure happens-before relations for intra-thread actions, i.e. there is no way of knowing if "PCCP" has been written before we call t2 that tries to read it. Thus, it is unsure whether we will get a value or none.


Synchronized keyword is introducing what Raul prefers an intrinsic monitor, i.e. a way to ensure thread-safety. Thus, introducing this keyword ensures that we can always print the value of s.find("PCPP");, as it has always been written before hand.

### 2
See code

# Exercise 3.3

## Notes

### 1

What we need to not end in a massive loop is for the write to x=42 -> before x == 0. If we do not, it will be stuck in the while loop. We can easily do this by adding the volatile keyword to x. Less easily, a monitor could be made to synchronize the read/write to x, but that's a bit too overcomplicated for this example.

### 2

In Main: 
HB_po = {
    t1.start()
    x = 42
    print
}

In T1:
HB_po = {
    init run
    read x (repeated) *effectively a while(true) loop as nothing indicates that the value of x can change*
    print
}

There is no synchronized HB relation between the two threads except for the initial t1.start()