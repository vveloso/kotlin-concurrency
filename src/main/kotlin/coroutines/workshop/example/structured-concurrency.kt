package coroutines.workshop.example

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    suspend fun one(): Int {
        println("Finding the answer to the most important question in universe...")
        delay(1_000)
        println("... found!")
        return 42
    }

    suspend fun two(): Int {
        println("Finding the question...")
//        throw ArithmeticException()
        delay(1_000)
        println("... asked!")
        return 1
    }

    try {
        coroutineScope {

            val een = async { one() }
            val twee = async { two() }

            println(een.await() + twee.await())

        }
    } catch (e: Exception) {
        println("Coroutines have thrown ${e::class.java}")
    }

}
