package coroutines.workshop.exercise3

import coroutines.workshop.common.logger
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

/*
 This is an exercise on cooperation between workers. At the same time, we'll
 see just how threads and coroutines have fundamental differences in what concerns
 concurrency.

 1. Run this code as is. Note down the result and the duration.

 2. Notice that the sumLeibniz() function has start and end arguments. Because it's a sum,
    sumLeibniz(0, 10) produces the same results as sumLeibniz(0, 5) + sumLeibniz(6, 10).
    Use this property to refactor the calculation into 10 separate factors.
    Check that you get approximately the same result as you did in 1). Note down the duration.

 3. Run each of those separate factors in its own thread. Note down the duration.

 4. Run each of those separate factors in its own coroutine. Note down the duration.

 5. Now experiment with yield() in the coroutines. Do the results differ?
 */

fun main() {

    warmUp() // this is so the JIT compiler will kick in, and we get more accurate measurements.

    val linearDuration = measureTimeMillis {
        calculatePiLinearly()
    }
    val threadsDuration = measureTimeMillis {
        calculatePiWithThreads()
    }
    val coroutinesDuration = measureTimeMillis {
        calculatePiWithCoroutines()
    }

    println("(linear)     Calculation took $linearDuration ms")
    println("(threads)    Calculation took $threadsDuration ms")
    println("(coroutines) Calculation took $coroutinesDuration ms")
}

fun calculatePiLinearly() = runBlocking {
    // approximate pi up to 9 digits: up to 1_000_000_000 terms
    val pi = sumLeibniz(0, 999_999_999) * 4
    println(pi) // (correct value of pi to 16 digits: 3.1415926535897932)
}

fun calculatePiWithThreads() {
    // approximate pi up to 9 digits: up to 1_000_000_000 terms
    val termCount = 1_000_000_000L
    val iterations = 10
    val interval = termCount / iterations

    val intermediateResults = DoubleArray(iterations)
    val threads = Array(iterations) {
        thread { intermediateResults[it] = runBlocking { sumLeibniz(it * interval, (it + 1) * interval - 1) } }
    }
    threads.forEach { it.join() }
    val pi = intermediateResults.sum() * 4

    println(pi) // (correct value of pi to 16 digits: 3.1415926535897932)
}

fun calculatePiWithCoroutines() = runBlocking {
    // approximate pi up to 9 digits: up to 1_000_000_000 terms
    val termCount = 1_000_000_000L
    val iterations = 10
    val interval = termCount / iterations

    val intermediateResults = DoubleArray(iterations)
    val coroutines = Array(iterations) {
        launch { intermediateResults[it] = sumLeibniz(it * interval, (it + 1) * interval - 1) }
    }
    coroutines.forEach { it.join() }
    val pi = intermediateResults.sum() * 4

    println(pi) // (correct value of pi to 16 digits: 3.1415926535897932)
}

suspend fun sumLeibniz(start: Long, end: Long): Double { // Leibniz series: 1 - 1/3 + 1/5 - 1/7 + 1/9 - .... ~ pi / 4

    logger.info("{} .. {}", start, end)

    var result = 0.0
    for (factor in start..end) {
        result += (if (factor % 2 == 0L) 1.0 else -1.0) / (1 + 2 * factor)
        if (factor % 100_000 == 0L) yield()
    }
    return result
}

fun warmUp() {

    repeat(5) {
        runBlocking { sumLeibniz(0, 1_000_000) }
    }

}
