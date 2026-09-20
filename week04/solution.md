# Exercise 4.1

## Files
BoundedBuffer.java

## Notes

---
### 1) Implement a class BoundedBuffer<T> as described above using only Java Semaphore for synchronization— i.e., Java Lock or intrinsic locks (synchronized) cannot be used.
---

See code

---
### 2) Explain why your implementation of BoundedBuffer<T> is thread-safe. Hint: Recall our definition of thread-safe class, and the elements to identify/consider in analyzing thread-safe classes (see slides).
---
Our implementation is thread-safe because of: \

**Mutual exclusion** on items and the pointers to make sure that they are handled in order without any race conditions. This is done with a binary semaphore: mutex \

**Correct blocking** ensures reading and writing will be blocked until conditions are met. This is done via counting semaphores notEmpty and notFull.

**No deadlock.** The conditional semaphores notEmpty / notFull are acquired before the mutex to ensure that they won't be holding mutex while blocked indefinitely.

**Cross-thread visiblity.** 
Insert item happens-before mutex.release() \
mutex.release() happens-before mutex.acquire() \
mutex.acquire() happens-before Take item \

By Transivitity this means that Insert item happens-before Take item and the synchonization actions of the mutex.release() ensures visibility of the data (JMM).

---
### 3) Is it possible to implement BoundedBuffer<T> using Barriers? Explain your answer.
---
It wouldn't make sense as barriers are used with a known amount of threads with a Wait for All policy. This bounded buffer is used with a dynamic amount of threads where writers and readers can come at any time. It would break the concept to use a barrier. Also producers and consumers have different conditions where a barrier would require all threads to have the same conditions.

---
### 4) One of the two constructors to Semaphore has an extra parameter named fair. Explain what it does, and explain if it matters in this example. If it does not matter in this example, find an example where it does matter.
---
We actually ended up using the fair flag, although it wasn't necessary. It ensures that producers and consumers waiting in line will be handled in the order they called .acquire(). This will ensure FIFO according to them, but problem actually only states FIFO of the takes and inserts which we ensure by using a take-pointer and an insert-pointer that both increments by 1 as items are respectively inserted or taken out. (This way the 3rd item inserted will be taken on the 3rd .take())

# Exercise 4.2

## Files
Person.java
PersonFactory.java

## Notes

---
### 1) Implement a thread-safe version of Person using Java intrinsic locks (synchronized). Hint: The Person class may include more attributes than those stated above; including static attributes.
---

See code

---
### 2) Explain why your implementation of the Person constructor is thread-safe, and why subsequent accesses to a created object will never refer to partially created objects.
---

By adding the synchronized keyword to all methods, we ensure mutual exclusion on accessing and changing details about a person.

---
### 3) Implement a program that starts several threads and each of the started threads creates and uses instances of the Person class. Run the program the program once. Did you observe any error in the behavior of the program?
--- 

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

---
### 4) Assuming that you did not find any errors when running 3. Is your experiment in 3 sufficient to prove that your implementation is thread-safe?
---

Not really. Each thread makes their own person and operates on that. Would have been more insightful to make persons first and then have the different threads play around with those persons.

# Exercise 4.3

## Files
CustomSemaphore.java
CustomBarrier.java

---
### 1) Implement a Semaphore thread-safe class using Java Lock. Use the description of semaphore provided in 2. the slides.
--- 

See CustomSemaphore.java

---
### 2) Implement a (non-cyclic) Barrier thread-safe class using your implementation of Semaphore above. Use the description of Barrier provided in the slides.
---

See CustomBarrier.java