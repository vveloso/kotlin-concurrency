package coroutines.workshop.exercise4

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.system.measureTimeMillis

/*
  The purpose of this exercise is to get ourselves started with cancellation of coroutines.

  1. Run the code as-is and take good note of how long the code takes to run on your machine.

  2. Refactor the code so that you use launch() to calculate and print the value of pi.
     This way you can use an instance of Job to manipulate the coroutine.

  3. Cancel the coroutine after half this time has passed and wait until it completes (after
     cancellation of course).

  4. Replace the explicit yield() with a check of the isActive coroutine property. Repeat and
     confirm the results.

  5. Remove the isActive check. Don't add the yield() again. Did the coroutine cancel as before?
 */
fun main() {

    val duration = measureTimeMillis {
        calculatePi()
    }

    println("Calculation took $duration ms")
}

fun calculatePi() = runBlocking {
    // approximate pi to 9 digits: up to 1_000_000_000 terms
    val pi = sumLeibniz(1_000_000_000) * 4
    println(pi) // (correct value of pi to 16 digits: 3.1415926535897932)
}

suspend fun sumLeibniz(end: Long): Double { // Leibniz series: 1 - 1/3 + 1/5 - 1/7 + 1/9 - .... = pi / 4
    var result = 0.0
    for (factor in 0..end) {
        result += (if (factor % 2 == 0L) 1.0 else -1.0) / (1 + 2 * factor)
        if (factor % 100_000 == 0L) yield()
    }
    return result
}
