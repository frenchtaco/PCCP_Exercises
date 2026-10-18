# Lecture 8: Performance measurements

Monday 19 October 2026


## Goals

The goals of this lecture are:

* Motivate the need for performance measurements, or benchmarking.
* Enable you to perform performance measurements of your own code.
* Enable you to a measure the performance of a number of Java
  constructs, including object creation; thread creation, start and
  termination for platform threads and virtual threads; sequential and
  parallel computation of prime numbers; memory hierarchy access
  times.
* Introduce the concept of virtual threads (since Java 21) and the
  distinction between platform threads and virtual threads.


## Readings 

* Peter Sestoft: [Microbenchmarks in Java, C# and C (PDF)](benchmarking.pdf)
  and [Supporting
  files](https://raspi.itu.dk/people/sestoft/benchmarking/), sections 1-4, 7, 9-11.
* Pardo, Sestoft, Staunstrup: 
  * [Scalability case
    studies](https://github.itu.dk/PCPP/PCPP2026-Public/blob/main/week09/pcppbook-ch9-scalability-20261015.pdf)
    only section 9.1: Platform threads, virtual threads and futures
* Java library [Thread class documentation](https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/lang/Thread.html)
* Java [virtual threads overview](https://docs.oracle.com/en/java/javase/26/core/virtual-threads.html)


## To do before lecture 8

* make sure you have a local copy on your computer of `code-exercises`
  (from same directory as this file)
* make sure that you can execute `gradle -PmainClass=exercises08.Benchmark
  run` in directory `week08/code-exercises/week08exercises` on your computer


## Lecture slides (TO APPEAR)
[lecture08.pdf]()


## Exercises

[exercises08.pdf](exercises08.pdf)
