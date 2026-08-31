# Lecture 3: Java Memory Model

## Goals

The goals of this lecture are:

* Define the notion of correctness in the Java memory model
* Analyze presence of data races in concurrent programs using the Java Memory Model
* Use concurrency primitives to construct correctly synchronized programs


## Readings 

* Pardo, Sestoft, Staunstrup:
  * [The Java Memory Model](../concurrency-notes/pcpp_concurrency_3.pdf).

* Goetz:
  * Chapter 16, Section 16.1.3.
  
### Optional readings

* Official documentation of the Java Memory Model in the Java Language Specification (JLS)
  * [Threads and Locks](https://docs.oracle.com/javase/specs/jls/se25/html/jls-17.html).

* Paper introducing the Java Memory Model
  * Jeremy Manson, William Pugh and Sarita V. Adve. [The Java Memory Model](https://dl-acm-org.kb-itu.idm.oclc.org/doi/abs/10.1145/1040305.1040336). In Proceedings of the 32nd ACM SIGPLAN-SIGACT symposium on Principles of programming languages (POPL'05).

* Bjørnar Haugstad Jåtten, Simon Boye Jørgensen, Rasmus Petersen, Raúl Pardo. [Scalable Thread-Safety Analysis of Java Classes with CodeQL](https://arxiv.org/abs/2509.02022). arXiv. eprint: 2509.02022. 2025.

## Lecture slides

See file [lecture03.pdf](./lecture03.pdf).

## Exercises

See file [exercises03.pdf](./exercises03.pdf).
