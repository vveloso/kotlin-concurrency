# Threading lightly with Kotlin

**Hands-on workshop**

Most developers have written or used concurrent code during their careers. 
Those who use Java are probably familiar with the traditional unit of concurrency: the Thread.

Kotlin brought first class support to a different paradigm, called _coroutines_.

This workshop intends to demonstrate the differences between concurrency models based on threads
and concurrency models based on coroutines. We will present coroutines to developers who have already 
used threads and answer questions such as:

* How different are coroutines and Threads?
* Are there any new pitfalls that developers should be aware of?
* Do the traditional synchronization methods available in the JRE still apply?

By the end of this workshop, participants will have a good grasp of the paradigm shift that 
Kotlin coroutines brought to the JVM.

### Prerequisites

You are expected to have basic notions of threading, concurrency, parallelism and synchronization. 

You only need a basic working knowledge of the Kotlin language.

### Activities

1. Basic principles behind coroutines (presentation).

1. Simple coroutine declaration and usage.
    * Bridging worlds with `runBlocking` functions.
    * The `launch` function.
    * The `suspend` keyword.

1. How lightweight are coroutines?
    * Create 100000 threads. What happens?
    * Create 100000 coroutines. What happens?

1. Cooperative vs. preemptive.
    * What happens if a coroutine misbehaves and blocks?
    * Explicit yielding.

1. Cancellation and timeouts.
    * The `Job` interface.
    * The `withTimeout` function.

1. Explicit concurrent execution of coroutines.
    * The `async` function.
    * The `Delayed` interface.
    * The `coroutineScope` function.

1. Shared mutable state...
    * Synchronization.

1. Generators (_flows_).
