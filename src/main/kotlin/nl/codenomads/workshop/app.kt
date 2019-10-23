package nl.codenomads.workshop

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    launch {
        println("Hello")
    }
    println("world")
}
