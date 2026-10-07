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
  and [Supporting files](https://raspi.itu.dk/people/sestoft/benchmarking/).  You may
  skip sections 5, 6, 12, 13.
* Java library [Thread class
  documentation](https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/lang/Thread.html)
* Java [virtual threads overview](https://docs.oracle.com/en/java/javase/26/core/virtual-threads.html)


## To do before lecture 9 (NOT UPDATED)

* make sure you have a local copy (on your computer) of  `code-exercises` (same folder as this file)
* test that you can run ` measurement.java ` (in this subdirectory ` week09/code-exercises/Week09exercises/app/src/main/java/exercises09 `)
for example by executing:

 ` gradle -PmainClass=exercises09.Measurement run `


## Lecture slides (TO APPEAR)
[lecture08.pdf]()


## Exercises (TO APPEAR)

[exercises08.pdf]()
