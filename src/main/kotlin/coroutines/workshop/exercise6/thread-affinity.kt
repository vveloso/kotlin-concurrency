package coroutines.workshop.exercise6

import coroutines.workshop.common.logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

fun main() = runBlocking {
    for(i in 0..10) {
        launch {
            logger.info("before delay in {}", i)
            delay(1000)
            logger.info("after delay in {}", i)
        }
    }
}
