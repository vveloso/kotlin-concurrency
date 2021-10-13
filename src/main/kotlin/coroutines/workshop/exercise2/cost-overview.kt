package coroutines.workshop.exercise2

import kotlin.concurrent.thread

/*
 This exercise has two parts:
    1. Launch 100000 threads with the loop below.
    2. Refactor the main() function to launch 100000 coroutines that also just "sleep"
       for one second each.
       Hint: remember runBlocking(), launch() and delay().

 Are there differences in results between the two approaches? Why?
 */
fun main() {
    var counter = 0
    try {
        repeat (100_000) {
            thread {
                Thread.sleep(1_000L)
            }
            ++counter
        }
    } finally {
        println("Launched $counter")
    }
}
