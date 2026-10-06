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

Finally, we can make it linearizable, as program-order will not break it, and thus:
`<q.enc(x), q.deq(x)>`

---
#### 7.1.4) Is this execution linearizable? If so, provide a linearization that satisfies the standard specification of a sequential FIFO queue. Otherwise, explain why it is not linearizable.
---

**Execution**: 

    A: ---|q.enq(x)|-----|q.enq(y)|-->
    B: --| q.deq(y) |->

**Answer**

This is NOT sequentially consistent, as a projection such as `q.enc(x), q.enc(y), q.deq(y)` would break the specification, as a queue dequeues from the head.

Since is it nos SC, it is not linearizable.


