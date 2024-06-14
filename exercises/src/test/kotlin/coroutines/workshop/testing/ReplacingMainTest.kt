package coroutines.workshop.testing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class ReplacingMainTest {

    private val mainDispatcherContext = newSingleThreadContext("UI thread")

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(mainDispatcherContext)
    }

    @AfterEach
    fun shutdown() {
        Dispatchers.resetMain()
    }

    @Test
    fun mainDispatcherTest() = runBlocking(Dispatchers.Main) {

    }
}