package nl.codenomads.workshop

import kotlin.system.measureTimeMillis

fun main() {

    val duration = measureTimeMillis {
        calculatePi()
    }

    println("Calculation took $duration ms")
}

fun calculatePi() {
    // pi to 9 digits: up to 1_000_000_000 terms
    val pi = sumLeibniz(1_000_000_000) * 4
    println(pi) // (correct value of pi to 16 digits: 3.1415926535897932)
}

fun sumLeibniz(end: Long): Double { // Leibniz series: 1 - 1/3 + 1/5 - 1/7 + 1/9 - .... = pi / 4
    var result = 0.0
    for (factor in 0..end) {
        result += (if (factor % 2 == 0L) 1.0 else -1.0) / (1 + 2 * factor)
    }
    return result
}
