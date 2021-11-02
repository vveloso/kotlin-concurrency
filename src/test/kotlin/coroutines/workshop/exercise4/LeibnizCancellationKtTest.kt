package coroutines.workshop.exercise4

import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.abs

internal class LeibnizCancellationKtTest {

    @Test
    fun testLeibniz() = runBlocking {
        val result = sumLeibniz(1_000_000)
        assert(abs(result - 0.785398) < 0.000001)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testDelayedCode() = runBlocking {
        val task = async {
            delay(10_000)
            42
        }
        assertEquals(42, task.await())
    }
}