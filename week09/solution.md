# Exercise 9.1

---
## 9.1.1+2
#### OS:   Windows 11; 10.0; amd64
#### JVM:  Oracle Corporation; 25
#### CPU:  Intel64 Family 6 Model 186 Stepping 2, GenuineIntel; 16 "cores"
#### Date: 2026-11-08T11:36:28+0100
name                |  threads    |      mean      |  deviation   |    count
--------------------|----------|----------------|--------------|-------------
countSequential     |          |   168573,6 us  |  3303,35     |     2
countParallelPlatf  |    1     |   167070,3 us  |  3358,66     |     2
countParallelVirtu  |    1     |   166764,6 us  |  4104,51     |     2
countParallelPlatf  |    2     |   135454,1 us  | 12480,35     |     2
countParallelVirtu  |    2     |   118272,1 us  |  7472,64     |     4
countParallelPlatf  |    3     |    96052,0 us  |  5462,95     |     4
countParallelVirtu  |    3     |    89303,7 us  |  4512,02     |     4
countParallelPlatf  |    4     |    82109,6 us  |  2968,19     |     4
countParallelVirtu  |    4     |    71528,1 us  |  4460,67     |     4
countParallelPlatf  |    5     |    63669,8 us  |  6591,13     |     4
countParallelVirtu  |    5     |    59736,8 us  |  2715,21     |     8
countParallelPlatf  |    6     |    58216,9 us  |  3187,47     |     8
countParallelVirtu  |    6     |    50659,8 us  |  1461,23     |     8
countParallelPlatf  |    7     |    49222,9 us  |  1020,93     |     8
countParallelVirtu  |    7     |    45344,8 us  |  1044,41     |     8
countParallelPlatf  |    8     |    44443,7 us  |   799,47     |     8
countParallelVirtu  |    8     |    41439,4 us  |  1273,69     |     8
countParallelPlatf  |   16     |    25538,5 us  |   463,97     |    16
countParallelVirtu  |   16     |    23687,9 us  |   230,61     |    16
countParallelPlatf  |   32     |    24227,3 us  |   181,02     |    16
countParallelVirtu  |   32     |    22271,3 us  |   515,71     |    16
countParallelPlatf  |   64     |    23446,1 us  |   184,48     |    16
countParallelVirtu  |   64     |    21398,8 us  |   455,02     |    16
countParallelPlatf  |  128     |    25035,8 us  |   680,04     |    16
countParallelVirtu  |  128     |    20921,4 us  |   196,97     |    16
countParallelPlatf  |  256     |    30263,8 us  |   326,90     |    16
countParallelVirtu  |  256     |    20575,6 us  |    50,03     |    16
countParallelPlatf  |  512     |    47974,1 us  |  4311,91     |     8
countParallelVirtu  |  512     |    20343,6 us  |   155,98     |    16
countParallelPlatf  | 1024     |    90762,4 us  | 16523,43     |     4
countParallelVirtu  | 1024     |    20329,5 us  |    77,16     |    16
countParallelPlatf  | 2048     |   171816,3 us  |  8440,80     |     2
countParallelVirtu  | 2048     |    20583,5 us  |   297,35     |    16

---
## 9.1.3
#### OS:   Windows 11; 10.0; amd64
#### JVM:  Oracle Corporation; 25
#### CPU:  Intel64 Family 6 Model 186 Stepping 2, GenuineIntel; 16 "cores"
#### Date: 2026-11-08T11:45:02+0100
name              |  threads     |      mean      |  deviation |      count
------------------|------------|----------------|------------|-----------------
countParallelTask |      1     |   169565,7 us  |  4097,03   |       2
countParallelTask |      2     |   116729,8 us  |  5967,03   |       4
countParallelTask |      3     |    93168,8 us  |  5708,67   |       4
countParallelTask |      4     |    72778,4 us  |  2928,58   |       4
countParallelTask |      5     |    57934,9 us  |  2543,50   |       8
countParallelTask |      6     |    51726,9 us  |  1483,98   |       8
countParallelTask |      7     |    44893,9 us  |  1510,69   |       8
countParallelTask |      8     |    40638,5 us  |   637,80   |       8
countParallelTask |     16     |    23758,5 us  |   437,82   |      16
countParallelTask |     32     |    22106,7 us  |   335,97   |      16
countParallelTask |     64     |    21285,9 us  |   178,75   |      16
countParallelTask |    128     |    21076,0 us  |   217,37   |      16
countParallelTask |    256     |    20578,2 us  |   121,74   |      16
countParallelTask |    512     |    20392,1 us  |   113,19   |      16
countParallelTask |   1024     |    20352,5 us  |   138,27   |      16
countParallelTask |   2048     |    20456,5 us  |    99,47   |      16
---
## 9.1.4
Time drastically goes down until the threads used reaches my computer's core count. After that, it just goes up and down but no noticable difference between lower or higher counts of threads for the virtual threads. The platform threads take longer and longer time the higher count of threads used above the amount of cores. At 2048 threads, platform threads actually go slower than sequential.


# Exercise 9.2

---
## 9.2.1+2+3+4+6

See file: HistogramPerformance\

SynchronizedHistogram | 

## 9.2.5

I'd argue that it resembles LongAdderArrayPadded as the object size alone of the atomic arrays makes sure that the different stripes live on (mostly) separate cachelines, which is what the whole "padding" ensures in the longadderarraypadded. 

# Exercise 9.3

## 9.3.1

#### OS:   Windows 11; 10.0; amd64
#### JVM:  Oracle Corporation; 25
#### CPU:  Intel64 Family 6 Model 186 Stepping 2, GenuineIntel; 16 "cores"
#### Date: 2026-11-09T05:09:55+0100

name                     | threads |      mean     |   deviation  |     count
-------------------------|---------|---------------|--------------|------------
SyncLongAdder            |  20     |  1587401.5 us | 218354.32    |      2
SyncLongArrayAdder       |  20     |   488348.2 us |  47950.96    |      2
SyncLongArrayAdderPadded |  20     |    77139.4 us |   4976.84    |      8
WrappedAtomicLong        |  20     |   671864.3 us | 170974.32    |      2
MyAtomicLong             |  20     |  2472944.3 us | 727498.82    |      2
ThreadLocalAtomicLong    |  20     |    34034.2 us |   1398.08    |     16
LongAdderArray           |  20     |   249704.4 us |  39805.96    |      2
LongAdderArrayPadded     |  20     |    47391.1 us |   5473.05    |      8
WrappedLongAdder         |  20     |    36857.2 us |   2846.74    |      8

Overall it looks as expected. Using stripes over single shared variables cut down time by a lot and furthermore adding padding to prevent false sharing improves it even more.

### Slowest, single shared variable, full contention
SyncLongAdder, MyAtomicLong, WrappedAtomicLong

### Middle, striped but shared cache lines - "false sharing"
SyncLongArrayAdder, LongAdderArray

### Fastest, striped and padded, no shared cache lines
SyncLongArrayAdderPadded, LongAdderArrayPadded, WrappedLongAdder, ThreadLocalAtomicLong

## 9.3.2

The padding ensures that each stripe lives on it's own cache line, which means that the different threads will never invalidate each other's work and every change should be able to take effect immediately. (No false sharing)

## 9.3.3
name                     |  threads|    mean       | deviation    | count
-------------------------|---------|---------------|--------------|--------
SyncLongAdder            |  20     |  1664512.2 us | 274710.18    |      2
SyncLongArrayAdder       |  20     |   342137.1 us |  46352.70    |      2
SyncLongArrayAdderPadded |  20     |    50371.1 us |   2382.35    |      8
SyncLongArrayAdderLessPad|  20     |   187622.7 us |  13376.43    |      2

The lock-array only holds references, so padding it alone doesn't make much of a difference, and every new object created gets placed back to back in the same cache lines, so when different stripes uses different locks, those locks will be sharing cache lines and we are back to false sharing.

## 9.3.4

# OS:   Windows 11; 10.0; amd64
# JVM:  Oracle Corporation; 25
# CPU:  Intel64 Family 6 Model 186 Stepping 2, GenuineIntel; 16 "cores"
# Date: 2026-11-09T05:45:38+0100

Memory latency: 134217728 identical operations k = arr[k]
name          |      bytes    |       mean      |   deviation    |   count
--------------|---------------|-----------------|----------------|-------
memory cover  |      8192 B   |     270020.7 us |    4044.98     |     2
memory cover  |     11584 B   |     272760.0 us |    5342.17     |     2
memory cover  |     16384 B   |     321575.5 us |  187365.97     |     2
memory cover  |     23172 B   |     213891.0 us |    1733.02     |     2
memory cover  |     32772 B   |     212615.0 us |     949.59     |     2
memory cover  |     46348 B   |     224427.9 us |   11989.88     |     2
memory cover  |     65544 B   |     336596.9 us |   10404.56     |     2
memory cover  |     92692 B   |     430834.4 us |    9959.63     |     2
memory cover  |    131088 B   |     497726.0 us |   25796.33     |     2
memory cover  |    185388 B   |    1997854.7 us |  951805.82     |     2
memory cover  |    262180 B   |    3400514.6 us | 1312862.97     |     2
memory cover  |    370780 B   |    1844056.1 us | 1523498.93     |     2
memory cover  |    524364 B   |    4311325.0 us | 1996978.19     |     2
memory cover  |    741564 B   |    1541816.0 us | 2259375.26     |     2
memory cover  |   1048728 B   |     871593.2 us |   40195.28     |     2
memory cover  |   1483124 B   |    1375126.4 us |   81184.87     |     2
memory cover  |   2097456 B   |    4215508.4 us | 5319349.51     |     2
memory cover  |   2966252 B   |   11331316.5 us | 9736460.08     |     2

