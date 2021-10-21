package coroutines.workshop.exercise6

import coroutines.workshop.common.logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

/*
  Run the program as is and note the thread name that is part of the logger's output.
  Experiment with the different Dispatchers to see how the output differs.
  Pay particular attention to the output of the Unconfined dispatcher.
 */

fun main() = runBlocking {
    for(i in 0..10) {
        launch {
            logger.info("before delay in {}", i)
            delay(1000)
            logger.info("after delay in {}", i)
        }
    }
}
