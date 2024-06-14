package coroutines.workshop.exercise6

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

fun main() = runBlocking {
    val log = LoggerFactory.getLogger("main")
    for(i in 0..10) {
        launch {
            log.info("before delay in {}", i)
            delay(1000)
            log.info("after delay in {}", i)
        }
    }
}
