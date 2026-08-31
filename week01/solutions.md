# Exercise 1.1

## Files:
TestLongCounterExperiments.java

## Notes:
### 1:
So, the result is never 20 million. This is probably due to a race condition in the code.

### 2:
When the count is so small, the first thread might finish before the second thread even starts, which eliminates the race condition - however it's somewhat luck-based.

### 3:
Using count = count + 1 over count++ consistently gets values closer to the expected value, but mostly the same result. We expected the compiler to create the same machine instructions though.

### 4:
The lock used ensures mutual exclusion to the critical section between threads which ensures that the value is accessed and updated correctly through each iteration by both threads.

### 5:
Our critical section only contains our count++ which is the line that increments the total. After the interleavings, the main thread waits for t1 and t2 using the .join() method, which ensures that once we call .get(), the count is complete and therefore doesn't need to be part of the critical section.

# Exercise 1.2

## Files:
TestPrinter.java

## Notes:

### 1:
Check solution in file

### 2:
Let's stage a succesful example of an interleaving: \
t1 -> calls print           \
t2 -> calls print           \
t1 -> prints (-)            \
t1 -> waits             \
t2 -> prints (-)            \
t2 -> waits             \
t1 -> prints (|)            \
t1 -> finishes print            \
t2 -> prints (|)            \
t2 -> finishes print            \

Let's stage an unsuccesful example of an interleaving: \
t1 -> calls print           \
t2 -> calls print           \
t1 -> prints (-)            \
t1 -> waits             \
t1 -> prints (|)            \
t2 -> prints (-)            \
t2 -> waits             \
t1 -> finishes print            \
t2 -> prints (|)            \
t2 -> finishes print            \
\
As seen in above examples, sometimes the threads may execute the print statements during the other thread's waiting time. Note that this is also possible without the 50ms wait, but much less likely.

### 3:
We created a lock and defined the critical section to be the first print, the wait and the second print, to ensure that only pairs of (-|) would be next to eachother. 

# Exercise 1.3:

## Files:
CounterThreads2Covid.java

## Notes:

### 1:
Check solution in file

### 2:
The lock defines a critical section where the count is checked and if lower than the max amount, it's incremented. Otherwise it will just unlock and move on. This ensures that no thread will ever add a 15.001st guest to the park.

# Exercise 1.4:

## Notes:

### 1:
Google docs fits perfectly into Goetz with fairness and convenience while working on a shared document. Looking at Nygaard's categories, it doesn't make as much sense to talk about it for this system.

### 2:
Mulitplayer games most often fit perfectly into each category. To play against others, you have to be able to take actions simultaneously, you need to exploit every bit of hardware in your system to render objects and players fast.

# Exercise 1.5

## Kåre
### 1:
Windows OS, native

### 2:
Windows OS
10 Cores
16GB RAM

NumberOfCores             : 10
NumberOfLogicalProcessors : 16
L2CacheSize               : 9728
L3CacheSize               : 24576

### 3:


## Victor

### 1:
Mac OS, native

### 2:
Mac OS
8 Cores
8GB RAM

### 3:
134.670 nanoseconds.
