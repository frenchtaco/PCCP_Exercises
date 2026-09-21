## Exercise 5.1) 

Note: Run tests using `$ gradle cleanTest test --tests <package>.<test_class>`

---

### 5.1) Implement a functional correctness test that finds concurrency errors in the add(Integer element) method in ConcurrentIntegerSetBuggy. Describe the interleaving that your test finds.
---

File: ConcurrentIntegerSet.java

We suppose that given that the values do not match on every single test, there is a scenario where thread1 reads a variable and increments it, however, thread2 reads the variable in between, causing a mismatch in values to a occur, i.e. a data race.
 

---
### 5.2) Implement a functional correctness test that finds concurrency errors in the remove(Integer element) method in ConcurrentIntegerSetBuggy. Descrfibe the interleaving that your test finds.

---

Thread1 wants to remove x, Thread2 wants to remove y. First, Thread1 reads the Set, finds the index wherein the value is that it wants to remove. It removes it. Then Thread 2 starts to read the Set, but the updated set from Thread 1 has not yet arrived. Thus, thread2 proceeds to remove y, but the set Thread 2 returns, there is still x, because it never read the new Set where x is gone.

---
### 5.3) In the class ConcurrentIntegerSetSync, implement fixes to the errors you found in the previous exercises. Run the tests again to increase your confidence that your updates fixed the problems. In addition, explain why your solution fixes the problems discovered by your tests.
---

We introduced the synchronized keyword on the `add()` and `remove()` to ensure mutual exclusion. We have not done it on `size()`, as it will be called after our threads terminate, and because of the termination rule, we didn't think we had to.  


---
### 5.4) Run your tests on the ConcurrentIntegerSetLibrary. Discuss the results
---

Here, the tests... pass. Here, a **SkipList** is used, which behaves in a way that is more atomic than the HashSet. While the code is identical, the underlying data structure's actions are atomic to other threads, and therefore, it does not need the intrinsic monitor to ensure that the thing is thread-safe, or that a data race can occur.

---
### 5.5) Do a failure on your tests above prove that the tested collection is not thread-safe? Explain your answer.
---

So, if a test fails, it tells us that the `add` and `remove` are not behaving the way that we want, and thus it tells us that it isn't thread-safe

---
### 5.6) Does passing your tests above prove that the tested collection is thread-safe (when only using add() and remove())? Explain your answer.
---

Even though tests are a way for us to look for thread-**un**safety, we cannot simply conlcude that thread-safety has occurred based on our tests cases, as we cannot ensure that all possible interleavings have been experimented with.

But I guess we could conclude that mutual exclusion has been ensured via the `synchronized`keyword, and thus that `add` and `remove` access can only happen on at a time.

---

## Exercise 5.2) 

## CALL HardKåre!!

---
### 5.1) Let capacity denote the final field capacity in SemaphoreImp. Then, the property above does not hold for SemaphoreImp. Your task is to provide an interleaving showing a counterexample of the property, and explain why the interleaving violates the property
---

I suppose that while the capacity, c, is final, there is nothing that stops our state from becoming lesser than. This is highlighted via our test, where we provoke, granted, a dual release to see if the state will go amock, which it did.

---
### 5.2) Write a functional correctness test that can trigger the interleaving you describe in 1. Explain why your test triggers the interlaving
---

Honestly, I think this test is wrong, and need to sit with Kåre and stare at it.