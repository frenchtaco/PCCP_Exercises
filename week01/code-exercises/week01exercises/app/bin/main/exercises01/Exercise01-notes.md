NB: Questions are answered both here and in the comments of the code.

## Exercise 1.1
####  Question 1) The main method creates a LongCounter object. Then it creates and starts two threads that run concurrently, and each increments the count field 10 million times by calling method increment. 

#### What output values do you get? Do you get the expected output, i.e., 20 million?

So, the result is never 20 million. This is probably due to a race condition in the code.

Please note, I ran the following using gradle `gradle -PmainClass=exercises01.TestLongCounterExperiments run`

#### Question 2) Reduce the counts value from 10 million to 100, recompile, and rerun the code. It is now likely that you get the expected result (200) in every run.

#### Explain how this could be. Is it guaranteed that the output is always 200?

According to AI, the reason for this is that the 1st thread executes all 100 increments and updates counts, before it switches to the second thread. In other words, with counts = 100, it goes so fast that no race condition ends up occurring. 

#### The increment method in LongCounter uses the assignment `count = count + 1;'` to add one to count. This could be expressed also as `count += 1` or as `count++`. Do you think it would make any difference to use one of these forms instead? Why? Change the code and run it. Do you see any difference in the results for any of these alternatives?

No change was observed.


#### Set the value of counts back to 10 million. Use Java ReentrantLock to ensure that the output of the program equals 20 million. Explain why your solution is correct, and why no other output is possible.
This has been accomplished by importing the reentrant lock:
`import java.util.concurrent.locks.ReentrantLock;`
After this, I want to he `LongCounter`class and wrapped the increment `count = count + 1;`in a `lock.lock();`and `lock.unlock)`. This has been done, seeing as this is the critical section, that is the area wherein both threads access shared memory.

## Exercise 1.2

#### Write a program