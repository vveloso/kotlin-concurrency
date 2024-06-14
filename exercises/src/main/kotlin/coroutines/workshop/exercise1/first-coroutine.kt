package coroutines.workshop.exercise1

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
 Coroutines have been created to allow explicit concurrency without much overhead.
 Therefore, several constructs were written to facilitate this purpose.

 runBlocking() and launch() are two of those constructs. The first creates a coroutine
 context and does not return until all coroutines started within it complete. The latter
 launches a coroutine concurrently in a different context but does not block or suspend
 the caller.

 Can you reason about the sequence in which the two strings appear in the console?
 What would happen if runBlocking() would not block? Why?
 */
fun main(): Unit = runBlocking {
    launch {
        println("Hello")
    }
    println("world")
}
