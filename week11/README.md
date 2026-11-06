# Lecture 11: Parallel Programming with Graphics Processors

Monday 9 November 2026


## Goals 

The goals of this lecture are:

* To understand and describe the difference in structure and intended
  use of Central Processing Unit (CPU) and Graphics Programming Unit
  (GPU).
* To understand and describe how a GPU is programmed, using matrix
  multiplication as an example. 
* To understand and discuss for what types of problems a GPU can
  achieve good performance.
* To understand  a CPU core's vector (SIMD, single instruction multiple data)
  instructions for core-internal parallel computations. 
* To be able to use a CPU core's vector instructions from Java to speed
  up numeric computations and text processing task, even on a single core. 


## Readings

* Pardo, Sestoft, Staunstrup: 
  * [The computer's CPU and RAM](pcppbook-ch8-cpu-ram-20261015.pdf)
* [Nvidia's CUDA C++ Programming Guide](https://docs.nvidia.com/cuda/pdf/CUDA_C_Programming_Guide.pdf) - for reference, certainly not all 598 pages.
* [Documentation of Java's vector instructions](https://docs.oracle.com/en/java/javase/26/docs/api/jdk.incubator.vector/jdk/incubator/vector/package-summary.html).  You will need in mostly FloatVector, ShortVector, VectorInstructions, VectorMask, and VectorSpecies.  



## Lecture slides (TO APPEAR)

[lecture11.pdf]()


## Exercises 

[exercises11.pdf](exercises11.pdf)
