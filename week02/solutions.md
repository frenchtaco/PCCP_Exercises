# Exercise 2.1

## Files:
Main run file: ReadersWriters.java \
Monitor file: SynchronizedMonitor.java \

## Notes:
### 1:
We implemented our monitor using the synchronized keyword in the methods.

### 2:
Our implementation allows writers to flag as "ready" which ensures that no new readers will be appended. This greatly increases the chance for the writer to be able to write, but not 100% as the others might cycle indefinitely through lock and condition variable 'queue', but it's highly unlikely.

### 3:
Since we are using synchronized, our monitor has one implicit wait set, which acts like one condition variable.

### 4:
Using ReentrantLock, we could use the flag "Fair" to ensure first in, first out of the queue, but otherwise we won't be able to get 100% absense of starvation.


# Exercise 2.2

## Files:
Main run file: TestMutableInteger.java

## Notes:
### 1:
We observe it looping forever. Since the int value can be stored in registers or low-level memory (caches), the value might not get shared between main thread and the created thread.

### 2:
Using synchronized keyword adds a lock / unlock feature to the program and unlocking flushes data from the low-level memory to higher-level (shared) memory, which ensures termination of the program.

### 3:
Using synchronized on get is unnecessary in this case as the locking mechanism on set flushes the data to shared memory and then the get can see the new value on both threads.

### 4:
Using volatile keyword ensures the value is saved in shared memory and since concurrency is not an issue in this program, we don't care that there's no lock.

# Exercise 2.3

## Files:
Main run file: TestLocking0.java

## Notes:
### 1:
We have 2 threads trying to add 1, 1 million times. One using static method and one using non-static. We see a race condition as the total sum doesn't get to 2 million.

### 2:
The static method's synchronized keyword puts a lock on the class itself, while the instance method puts a lock on the instance of the class. Each lock work independently and in this case does nothing to prevent a race condition between the two threads.

### 3:
We made a new object called lock which we could then use synchronized keyword in both methods to ensure a shared lock. This made the synchronized keyword in the method itself obsolete, so we took the liberty of removing it. However it would also work with it still on.

### 4:
The lock doesn't actually lock for both methods and is obsolete since the main method is waiting for both threads to complete using .join() and therefor ensures that the sum is complete when called.