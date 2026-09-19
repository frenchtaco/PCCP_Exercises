# Exercise 4.1

## Files
BoundedBuffer.java

## Notes

### 1
See code

### 2
Our implementation is thread-safe because of: \

**Mutual exclusion** on items and the pointers to make sure that they are handled in order without any race conditions. This is done with a binary semaphore: mutex \

**Correct blocking** ensures reading and writing will be blocked until conditions are met. This is done via counting semaphores notEmpty and notFull.

**No deadlock.** The conditional semaphores notEmpty / notFull are acquired before the mutex to ensure that they won't be holding mutex while blocked indefinitely.

**Cross-thread visiblity.** 
Insert item happens-before mutex.release() \
mutex.release() happens-before mutex.acquire() \
mutex.acquire() happens-before Take item \

By Transivitity this means that Insert item happens-before Take item and the synchonization actions of the mutex.release() ensures visibility of the data (JMM).

### 3

It wouldn't make sense as barriers are used with a known amount of threads with a Wait for All policy. This bounded buffer is used with a dynamic amount of threads where writers and readers can come at any time. It would break the concept to use a barrier. Also producers and consumers have different conditions where a barrier would require all threads to have the same conditions.

### 4

We actually ended up using the fair flag, although it wasn't necessary. It ensures that producers and consumers waiting in line will be handled in the order they called .acquire(). This will ensure FIFO according to them, but problem actually only states FIFO of the takes and inserts which we ensure by using a take-pointer and an insert-pointer that both increments by 1 as items are respectively inserted or taken out. (This way the 3rd item inserted will be taken on the 3rd .take())

# Exercise 4.2

## Files
Person.java
PersonFactory.java

## Notes

### 1
See code

### 2
By adding the synchronized keyword to all methods, we ensure mutual exclusion on accessing and changing details about a person.

### 3
It seems to run as it should.

Output: \
11 Bob 1234 Nowhere special \
11 Hans 2468 Hans' Home \
9 Bob 1234 Nowhere special \
7 Bob 1234 Nowhere special \
7 Hans 2468 Hans' Home \
10 Bob 1234 Nowhere special \
10 Hans 2468 Hans' Home \
8 Hans 2468 Hans' Home \
9 Hans 2468 Hans' Home \

### 4
Not really. Each thread makes their own person and operates on that. Would have been more insightful to make persons first and then have the different threads play around with those persons.

# Exercise 4.3

## Files
CustomSemaphore.java
CustomBarrier.java

### 1
See CustomSemaphore.java

### 2
See CustomBarrier.java