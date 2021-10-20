package coroutines.workshop.exercise4

import coroutines.workshop.common.logger
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis

/*
  The purpose of this exercise is to get ourselves started with cancellation of coroutines.

  1. Run the code as-is and take good note of how long the code takes to run on your machine.

  2. Refactor the code so that you use launch() to calculate and print the value of pi.
     This way you can use an instance of Job to manipulate the coroutine.

  3. Cancel the coroutine after half this time has passed and wait until it completes (after
     cancellation of course).

  4. Replace the explicit yield() with a check of the coroutineContext.isActive coroutine context property.
     Repeat and note the results.

  5. Remove the isActive check. Add the yield() again. Did the coroutine cancel as before?
 */

fun main() {

    val duration = measureTimeMillis {
        calculatePi()
    }

    logger.info("Calculation took $duration ms")
}

fun calculatePi() = runBlocking {
    logger.info("Starting calculations")
    // approximate pi to 9 digits: up to 1_000_000_000 terms
    val job = launch {
        logger.info("pi = ${sumLeibniz(1_000_000_000) * 4}")
    }
    delay(500)
    logger.info("Cancelling...")
    job.cancel()
    job.join()
    logger.info("Finished.")
}

suspend fun sumLeibniz(end: Long): Double { // Leibniz series: 1 - 1/3 + 1/5 - 1/7 + 1/9 - .... = pi / 4
    var result = 0.0
    logger.info("Calculating...")
    for (factor in 0..end) {
        result += (if (factor % 2 == 0L) 1.0 else -1.0) / (1 + 2 * factor)
        if (factor % 100_000 == 0L) yield()
    }
    logger.info("Calculated $result")
    return result
}
