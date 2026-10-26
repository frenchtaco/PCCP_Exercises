# Exercise 8.1

---
## 8.1.1 
---
### Specifications

#### JVM:  Oracle Corporation; 25
#### CPU:  Intel64 Family 6 Model 186 Stepping 2, GenuineIntel; 16 "cores"
#### Date: 2026-10-26T00:21:02+0100

### First run:

pow                                  14,2 ns       0,82   16777216\
exp                                  10,4 ns       3,93   67108864\
log                                  16,1 ns       2,78   16777216\
sin                                  26,2 ns       2,12   16777216\
cos                                  10,6 ns       0,08   33554432\
tan                                  14,8 ns       0,15   33554432\
asin                                 10,4 ns       5,71   67108864\
acos                                  7,2 ns       0,07   67108864\
atan                                  7,6 ns       0,07   33554432\


### Second run:

pow                                  13,8 ns       0,08   33554432\
exp                                   6,0 ns       0,07   67108864\
log                                   8,2 ns       0,15   33554432\
sin                                  10,0 ns       0,07   33554432\
cos                                  10,4 ns       0,07   33554432\
tan                                  14,8 ns       0,12   33554432\
asin                                  7,3 ns       0,09   67108864\
acos                                  7,1 ns       0,08   67108864\
atan                                  7,6 ns       0,04   33554432\

### Analysis
First run was very "disturbed". cos and sin should take about the same time, but they were very long apart. Overall it was very scattered.\
Second run was much faster and more consistent overall.

## 8.1.2

### 1st
sqrt                                  1,9 ns       0,04  134217728 \
random                               32,0 ns       2,30    8388608 \

### 2nd
sqrt                                  1,9 ns       0,02  134217728 \
random                               23,1 ns       0,53   16777216 \

### 3rd
sqrt                                  1,9 ns       0,01  134217728 \
random                               22,9 ns       0,17   16777216 \

### Analysis
sqrt is very fast as expected as it's built in with a single instruction, but random seems kinda slow.

## 8.1.3

### 3 runs
sqrt                                 23,4 ns       0,12   16777216

### Analysis
Very consistent result and slightly lower than doing both operations simultaniously. Makes sense since it might reduce overhead to run them together.

# Exercise 8.2

## 8.2.1

Results are very volatile with low count and stabilizes as count goes up. Also the mean drops drastically.

## 8.2.2

### OS:   Windows 11; 10.0; amd64
### JVM:  Oracle Corporation; 25
### CPU:  Intel64 Family 6 Model 186 Stepping 2, GenuineIntel; 16 "cores"
### Date: 2026-10-26T00:49:44+0100

hashCode()                            2,6 ns       0,09  134217728\
Point creation                       32,3 ns       0,46    8388608\
Thread's work                      5729,8 ns      34,47      65536\
Thread pltf cre                      97,1 ns       1,67    4194304\
Thread pltf cre strt             250209,5 ns   49742,45       2048\
Thread pltf cre strt join        119676,2 ns    8446,30       4096\
Thread virt cre                      46,6 ns       1,33    8388608\
Thread virt cre strt join         13391,7 ns     142,02      32768\
Uncontended lock                     21,8 ns       0,10   16777216\

### Analysis

| Benchmark                 | Lecture   | Mine       | Ratio  |
|---------------------------|-----------|------------|--------|
| hashCode()                | 1-2 ns    | 2,6 ns     | ~1,5×  |
| Point creation            | 33 ns     | 32,3 ns    | 1,0×   |
| Thread's work             | 1730 ns   | 5730 ns    | 3,3×   |
| Thread pltf cre           | 53 ns     | 97 ns      | 1,8×   |
| Thread virt cre           | 35 ns     | 47 ns      | 1,3×   |
| Thread pltf cre strt      | 23000 ns  | 250210 ns  | 11×    |
| Thread pltf cre strt join | 30400 ns  | 119676 ns  | 3,9×   |
| Thread virt cre strt join | 7100 ns   | 13392 ns   | 1,9×   |
| Uncontended lock          | 4 ns      | 21,8 ns    | 5,5×   |

* AI used extract the values from the lecture slides.\

Overall my computer produces slower results, but the overall idea is the same. Platform threads are expensive so shouldn't be used for small calculations and virtual threads are much cheaper. Platforms in particular is so much slower on my computer. The 11x increase could be a little misleading as they are never joined, so they are still running and piling up I suppose?

## 8.2.3

Following the lecture, the overhead of a thread is the total time for
create + start + join minus the time for the work the thread does
(1000 calls to `getAndIncrement`, measured separately as "Thread's work"):

| | Total (cre+strt+join) | Thread's work | Overhead |
|---|---|---|---|
| Platform thread | 119676 ns | 5730 ns | **113946 ns ≈ 114 µs** |
| Virtual thread  | 13392 ns  | 5730 ns | **7662 ns ≈ 7,7 µs** |

So creating, starting and joining a virtual thread is about
113946 / 7662 ≈ **15× cheaper** than a platform thread on my machine.\

Virtual threads still have a lot of overhead compared to the amount of work done here, so they are not free either and would probably be better left out for smaller calculations.

# Exercise 8.3

## 8.3.1
quicksort                          8945,3 us      55,57         32

## 8.3.2
Arrays.sort                        6441,0 us     114,13         64

Yes, java's library method is quite a lot faster.

## 8.3.3
Arrays.parallelSort                1373,8 us      28,38        256

This sort uses 15 threads that runs in parallel. It's much faster, but it's nowhere near 15x faster, so starting, running and joining the threads cost a lot of time in the big picture.

## 8.3.4
parallel quicksort                11251,5 us     145,97         32

Much slower than the sequential quicksort. This is due to creating a lot new virtual threads which overall cost a lot due to overhead. Some of these threads will sort less than 10 elements, which takes no time, but still creates a large overhead. Would be smarter to work parallel on larger subarrays and keep it sequential once they start getting small.

## 8.3.5
parallel quicksort                 1825,0 us      16,24        256

As suggested in the previous question, putting a limit to switch between sequential and parallel quicksort helps reduce time a lot. Still not getting quite to java's built-in parallelSort but getting closer.

# Exercise 8.4

## 8.4.1
callAdd                             425,2 us     108,16        512

## 8.4.2
callAddVolatile                    9642,8 us     202,06         32

Each measurement covers creating a `LongHolder` and doing 1 000 000
additions, so the cost per addition is:

- non-volatile: 425,2 us / 1,000,000 ≈ **0,43 ns** per addition
- volatile: 9642,8 us / 1,000,000 ≈ **9,6 ns** per addition

When making the field volatile, each addition must be visible to other threads, so the value can't stay in the register as without it. This creates a lot of overhead which in this case result in close to 20x more time spent.

It's interesting looking at the standard deviation as even though the mean spikes a lot, the deviation is only doubled. This can be explained by the small operations in the first run gets affected by small **noises** and disturbences a lot more than the volatile run.