package nl.codenomads.workshop

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.system.measureTimeMillis

fun main() {

    val duration = measureTimeMillis {
        calculatePi()
    }

    println("Calculation took $duration ms")
}

fun calculatePi() = runBlocking {
    // pi to 9 digits: up to 1_000_000_000 terms
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
