### 3.1.1) List the possible actions in the executions of the program. Classify the actions as: variable access, synchronization, or other. You may assign numbers to operations in the program to easily refer to them in this exercise and the ones below

An action is represents an execution of an operatin inside a thread. These cover variable accesses and synchronization actions. 

count = 0 //variable access

t1, t2 = new CountingThread(); // write action
t1.start(), t2.start() // sync action

temp, count // write/read

t1.join(), t2.join() //sync action

### 3.1.2) Define the happens-before order set containing action pairs obtained from the program order rule. Is this set the same for all possible program executions?

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

### 3.1.2) Define the set of all possible synchronization orders for this program

Let int temp = count; be (1), and count = temp + 1; be (2).

1) m(start(t1)), t1(1), t1(2), m(start(t2)) t2(1), t2(2)

2) m(start(t1)), m(start(t2)) t1(1), t2(1), t1(2), t2(2)

3) m(start(t2)), m(start(t1)) t2(1), t1(1), t2(2), t1(2)

4) m(start(t2)), t2(1), t2(2), m(start(t1)) t1(1), t1(2)

### 3.1.5) 5. Show, using the Java memory model, that this program contains data races.
While the JMM (Java Memory Model) provides program order, there is no happens-before order, i.e. no sync order between the threads. Thus, there is no way of knowing if count will be 1 or 2 at the end of execution.

### 3.1.6) Use a lock to eliminate the data races in the program. Explicitly list the changes in program order and happens-before order compared to what you answered in the previous parts of the exercise. You do not need to explicitly write the new set of synchronization orders, but you must argue whether the possible executions in the new set of synchronization orders gives rise to more than one happens-before order set. 

By using a delay to highlight the data race, and a lock, we have shown that a data race has been avoided. Hence, what we are ensuring now is: 

1) m(start(t1)), t1(1), t1(2), m(start(t2)) t2(1), t2(2)

### 3.1.7) Show, using the Java Memory model, that your uploaded program does not contain data races

We know that t1(1) -> t1(2) -> t2(1) -> t2(2)

### 3.1.8) Consider again the program without the lock, but defining x as volatile. Are all executions of this version of the program data race free? If you answer yes, use the Java memory model to show that the program does not contain data races. If you answer no, use the Java memory model to show the program contains data races

The volatile keyword does not ensure mutual exclusion, and given that temp is instantiated inside the `CountingThread`class, it cannot be volatile. Regardless, since we cannot ensure mutual exclusion, we cannot avoid the data race.


### 3.2.1) Show, using the Java memory model, that the program is not correctly synchronized
The memory model does not ensure happens-before relations for intra-thread actions, i.e. there is no way of knowing if "PCCP" has been written before we call t2 that tries to read it. Thus, it is unsure whether we will get a value or none.


Synchronized keyword is introducing what Raul prefers an intrinsic monitor, i.e. a way to ensure thread-safety. Thus, introducing this keyword ensures that we can always print the value of s.find("PCPP");, as it has always been written before hand.

### 3.3.1) Last week we saw that (a similar version of) this program can loop forever, i.e., there exist executions where the operation while(x==0) is executed forever by thread t1. What pair of actions must be ordered by happens-before to prevent these infinite executions?

What we need to not end in a massiver loop is for the write to x=42 -> before x == 0. If we do not, it will be stuck in the while loop.

3.3.2) 