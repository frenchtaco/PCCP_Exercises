## Exercise 7.1

---
#### 7.1.1) Is this execution sequentially consistent? If so, provide a sequential execution that satisfies the standard specification of a sequential FIFO queue. Otherwise, explain why it is not sequentially consistent.
---

**Definition**: sequential consitency is defined by two properties:
1) Program order is respected: for each thread, its calls appear in the same order as that thread issued them.
2) The sequential specification is satisfied:

**The execution**:


    A: ---------------|q.enq(x)|--|q.enq(y)|->
    B: ---|q.deq(x)|------------------------->

**Answer** 
We need only one sequential projection for this to be sequentially consistent. We see two constraints:
1) Program order for thread A, namely that |q.enq(x)| *happens before* |q.enq(y)|, and 
2) The specification is another constraint, specifically that you cannot call |q.deq(x)| before you call |q.enq(x)|, as this would result in an error.

We can create the following projection, and therefore conclude that the execution is sequentially consistent.

`<q.enq(x), q.enq(y), q.deq(x)>`




---
#### 7.1.2) Is this execution (same as above) linearizable? If so, provide a linearization that satisfies the standard specification of a sequential FIFO queue. Otherwise, explain why it is not linearizable.
---

**Definition**: Linearizability extends sequential consistency, so the two constraints there have to hold along with real-time ordering of the executions. 

**Execution**

    A: ---------------|q.enq(x)|--|q.enq(y)|->
    B: ---|q.deq(x)|------------------------->


**Answer**: 
Here, the specification conflicts with the real-time ordering requirement from linearizability. `q.deq(x)` cannot happen before `q.enq(x)`, unless the spec requirement will not hold. But `q.deq(x)` has to happen before `q.enq(x)` if real-time ordering has to hold. 

Thus, this is NOT linearizable.

Ps. it is not possible to make linearization points in a way where the method calls overlap, i.e. NOT linerizable

---
#### 7.1.3) Is this execution linearizable? If so, provide a linearization that satisfies the standard specification of a sequential FIFO queue. Otherwise, explain why it is not linearizable.
---

**Execution**: 

    A: ---| q.enq(x) |-->
    B: ------|q.deq(x)|--------------->

So, here we need:
1) **Program order**
2) **The specification to not be broken**

and for linearizability: 

3) **real-time order**

Program order is not an issue since there's only one execution per thread. The specification will not be broken either, seeing as we can create a projection wherein `q.enc(x)` happens before `q.deq(x)`.

Finally, we can make it linearizable, as the two previous requirements will not be violated when real-time ordering is introduced (along with linearization points for the method calls that overlap):
`<q.enc(x), q.deq(x)>`

---
#### 7.1.4) Is this execution linearizable? If so, provide a linearization that satisfies the standard specification of a sequential FIFO queue. Otherwise, explain why it is not linearizable.
---

**Execution**: 

    A: ---|q.enq(x)|-----|q.enq(y)|-->
    B: --|       q.deq(y)         |-->

**Answer**

This is NOT sequentially consistent, as a projection such as `q.enc(x), q.enc(y), q.deq(y)` would break the specification, as a queue dequeues from the head.

Since is it nos SC, it is not linearizable.

---

## Exercise 7.2

---
#### 7.2.1) Define linearization points for the push and pop methods in the Treiber Stack code provided in app/src/ main/java/exercises07/LockFreeStack.java. Explain why those linearization points show that the implementation of the Treiber Stack is linearizable

We have set our linearization points at the `oldHead = top.get();`and the `CAS-operation` in both methods, meaning that there is an interleaving that:
1) Does not break program order (as there is none in our example), and 
2) Does not break the specification of the stack, and 
3) Has real-timer ordering with linearization points that overlap


    A: ---|    q.push(x)  |------->
    -----------|  ------|
    B: -------|    q.pop(x) |---->


---
#### 7.2.2) Write a JUnit functional correctness test for the push method of the Treiber Stack. Consider a stack of integers. The test must assert that after n threads push integers x1, x2, . . . , xn, respectively, the total sum of the elements in the stack equalsn i=1 xi. Write your test in the test skeleton file app/src/test/java/ exercises07/TestLockFreeStack.java
---
See the TestLockFreeStack.java

---
#### 7.2.3) Write a JUnit functional correctness test for the pop method of the Treiber Stack. As before, consider a stack of integers. Given a stack with n elements x1, x2, . . . , xn already pushed, the test must assert the following: after n threads pop one element yi each, the sum of popped elements equals the sum of elements originally in the stackn i=1 xi = n i=1 yi. Write your test in the test skeleton file app/src/test/java/ exercises07/TestLockFreeStack.java.
---
See the TestLockFreeStack.java

---
#### 7.2.4) Do the tests in part 2. and 3. cover all linearization points in the Treiber Stack? Explain your answer. If you answered that not all linearization points were covered, then add additional concurrent functional tests to cover all linearization points.
---
We have tested for non-empty `push`and `pop`, but not for empty `pop`, which is one of our linearization points.
- see file for the added test
---

## Exercise 7.3

---
#### 7.3.1) Consider the reader-writer locks exercise from week 6. There are four methods included in this type of locks: writerTryLock, writerUnlock, readerTryLock and readerUnlock. State, for each method, whether they are wait-free, lock-free or obstruction-free and explain your answers.

