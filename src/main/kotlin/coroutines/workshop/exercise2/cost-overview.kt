package coroutines.workshop.exercise2

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep
import kotlin.concurrent.thread

/*
 This exercise has two parts:
    1. Launch 100000 threads with the loop below.
    2. Refactor the main() function to launch 100000 coroutines that also just "sleep"
       for one second each.
       Hint: remember runBlocking(), launch() and delay().

 Are there differences in results between the two approaches? Why?
 */
fun main() = runBlocking {
    var counter = 0
    try {
        repeat (100_000) {
            launch {
                delay(1_000L)
                print('.')
            }
            ++counter
        }
    } finally {
        println("Launched $counter")
    }
}
