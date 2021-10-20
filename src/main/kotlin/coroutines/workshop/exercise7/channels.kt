package coroutines.workshop.exercise7

import coroutines.workshop.common.logger

/*
    Your task:

    Connect two coroutines using channels. One produces things, the other consumes those things.
    Print the things to console as they are consumed.

    Remember to use the channel interface: kotlinx.coroutines.channels.Channel<E>

    Optional:

    Try out the experimental produce() method in CoroutineScope
    (see https://github.com/kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/jvm/test/guide/example-channel-03.kt)
 */

fun main() {
    logger.info("I'm done with all this producing and consuming.")
}
