# Lecture 9: Performance and Scalability

Monday 26 October 2026


## Goals

The goals of this lecture are:
* To be able to use platform threads, virtual threads and Java's
  executor framework and futures
* To describe key obstacles to performance and scalability on shared
  memory multi-core computers
* To describe and use techniques to improve performance, such as lock
  striping, better cache usage, immutable data, avoidance of false
  sharing, and so on, as illustrated by case studies of concurrent
  data structures and algorithms


## Readings (TO BE UPDATED)

* Goetz Chapter 5, 6.2, 6.3 (up to 6.3.4), and  11 
* [Java documentation of Executors](https://docs.oracle.com/javase/tutorial/essential/concurrency/executors.html) including the three subsections (Executor Interfaces, Thread Pools and Fork/Join).
* Pardo, Sestoft, Staunstrup: 
  * [The computer's CPU and RAM](pcppbook-ch8-cpu-ram-20261015.pdf)
  * [Scalability case studies](pcppbook-ch9-scalability-20261015.pdf)
    sections 9.1, 9.2 and 9.3 (the latter only cursory)
* [Java documentation of ConcurrentHashMap](https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/util/concurrent/ConcurrentHashMap.html)


## Lecture slides (TO APPEAR)

[lecture09.pdf ]()


## Exercises (TO APPEAR)

[exercises09.pdf]()
