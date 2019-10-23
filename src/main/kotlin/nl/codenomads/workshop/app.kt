package nl.codenomads.workshop

import kotlin.concurrent.thread

fun main() {
    var counter = 0
    try {
        repeat (100_000) {
            thread {
                Thread.sleep(1000L)
            }
            ++counter
        }
    } finally {
        println("Launched $counter")
    }
}
