# Exercise 3.1.

## Files
CountingThreads.java

## Notes
--- 
### 1) List the possible actions in the executions of the program. Classify the actions as: variable access, syn- chronization, or other. You may assign numbers to operations in the program to easily refer to them in this exercise and the ones below.
--- 
An action represents an execution of an operatin inside a thread. These cover variable accesses and synchronization actions. 

`count = 0` is a **write action**

`t1, t2 = new CountingThread();` is a **write action**, object creation

`t1.start(), t2.start()` is a **sync action**

`temp = count` **write** temp, **read** count

`count = temp + 1`, **write** count, **read** temp

`t1.join(), t2.join()` is a **sync action**

---
### 2) Define the happens-before order set containing action pairs obtained from the program order rule. Is this set the same for all possible program executions?
---


**HB_m** = 

    m(write(count))

    m(start(t1)) →
    m(start(t2)) →

    m(join(t1)) →
    m(join(t2)) →

    m(print(count))


**HB_t1** = 

    t1(read(count)) → t1(write(temp)) →
    t1(read(temp)) → t1(write(count))


**HB_t2** =

    t2(read(count)) → t2(write(temp)) →
    t2(read(temp)) → t2(write(count))

---
### 3) Define the happens-before order set containing action pairs obtained from the thread start rule and the thread termination rule. Is this set the same for all possible program executions?
---
Note: The complete happens-before set is the **transitive closure of the union of the happens-before sets** below.

**Full Set** 

    m(write(count)) →

    m(start(t1)) →
    m(start(t2)) →
    
        t1(read(count)) → t1(write(temp)) → t1(read(temp)) → t1(write(count))
    
        t2(read(count)) → t2(write(temp)) → t2(read(temp)) → t2(write(count))
    m(join(t1)) →
    m(join(t2)) →

    m(print(count))


---
### 4) Define the set of all possible synchronization orders for this program.
---
Let `int temp = count`; be **(1)**,
and `count = temp + 1`; be **(2)**,
and start of a thread be **S(t)**

**On Program Order**
First, we have Program Order, which tells us that: 
1) **S(1) → S(2)**, and 
2) **t1(1) → t2(2)**, and **t2(1) → t2(2)**

**On Start Rule** 
3) Then, we have that **S(1) → t1(1)** and **S2 → t2(1)**

The full set of all possible synchronization orders is therefore all possible interleavings that do not violate these 3 things.

---
### 5 Show, using the Java memory model, that this program contains data races.
--- 

While the JMM (Java Memory Model) provides program order, there is no happens-before order, i.e. no sync order between the threads. Thus, there is no way of knowing if count will be 1 or 2 at the end of execution.

### 6) Use a lock to eliminate the data races in the program
##### File: CountingThreads.java
By using a delay to highlight the data race, and a lock, we have shown that a data race has been avoided. Hence, what we are ensuring now is: 

1) m(start(t1)), t1(1), t1(2), m(start(t2)) t2(1), t2(2)

---
### 7) Show, using the Java memory model, that your updated program (3.1.6) does not contain data races.
---

We suppose that since we have now eliminated data races, we have ensured that program order exists and we know that 
    
    t1(lock) → t1(1) -> t1(2) -> t1(unlock) →
    t2(lock) → t2(1) -> t2(2) → t2(unlock)

---
### 8) Consider again the program without the lock, but defining count as volatile. Are all executions of this version of the program data race free?
--- 

The volatile keyword does not ensure mutual exclusion, and given that temp is instantiated inside the `CountingThread`class, it cannot be volatile. Regardless, since we cannot ensure mutual exclusion, we cannot avoid the data race.

# Exercise 3.2

## Files
TestStringSet.java

## Notes
---
### 1) Show, using the Java memory model, that the program is not correctly synchronized.
---
The memory model does not ensure happens-before relations for intra-thread actions, i.e. there is no way of knowing if "PCCP" has been written before we call t2 that tries to read it. Thus, it is unsure whether we will get a value or none.


Synchronized keyword is introducing what Raul prefers an intrinsic monitor, i.e. a way to ensure thread-safety. Thus, introducing this keyword ensures that we can always print the value of s.find("PCPP");, as it has always been written before hand.

### 2) Add the modifier synchronized to the definition of the method find(...) in the class StringSet. Show, using the Java memory model, whether the program is correctly synchronized. The notes for 3.2.1 also apply in this exercise.

See code

# Exercise 3.3

## Notes

---
### 1) Last week we saw that (a similar version of) this program can loop forever, i.e., there exist executions where the operation while(x==0) is executed forever by thread t1. What pair of actions must be ordered by happens-before to prevent these infinite executions?
---

What we need to not end in a massive loop is for the write to x=42 -> before x == 0. If we do not, it will be stuck in the while loop. We can easily do this by adding the volatile keyword to x. Less easily, a monitor could be made to synchronize the read/write to x, but that's a bit too overcomplicated for this example.

---
### 2) Show, using the Java memory model, that the pair of actions you identified in 3.3.1 are not ordered by happens-before in all executions of the program.
--- 


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