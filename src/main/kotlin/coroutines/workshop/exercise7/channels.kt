package coroutines.workshop.exercise7

import coroutines.workshop.common.logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
    Your task:

    Connect two coroutines using channels. One produces things, the other consumes those things.
    Print the things to console as they are consumed.

    Remember to use the channel interface: kotlinx.coroutines.channels.Channel<E>

    Optional:

    Try out the experimental produce() method in CoroutineScope
    (see https://github.com/kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/jvm/test/guide/example-channel-03.kt)
 */

@ExperimentalCoroutinesApi
fun main() {

    exampleClosingChannel()

    exampleLimitedReception()

    exampleUsingProduce()

    exampleFanOut()

    exampleFanIn()

    exampleChaining()

    logger.info("I'm done with all this producing and consuming.")
}

fun exampleClosingChannel() = runBlocking {
    logger.info("Closing:")
    val channel = Channel<String>()
    launch { arrayOf("Eve", "John", "Mary").forEach { channel.send(it) }; channel.close(); }
    launch { channel.consumeEach { logger.info(it) } }
}

fun exampleLimitedReception() = runBlocking {
    logger.info("Limited:")
    val channel = Channel<String>()
    launch { channelProducer(channel, arrayOf("Eve", "John", "Mary")) }
    launch { limitedReceiver(channel) }
}

suspend fun channelProducer(channel: SendChannel<String>, names: Array<String>) {
    names.forEach { channel.send(it) }
}

suspend fun limitedReceiver(channel: ReceiveChannel<String>, limit: Int = 3) {
    repeat(limit) { logger.info(channel.receive()) }
}

@ExperimentalCoroutinesApi
fun exampleUsingProduce() = runBlocking {
    logger.info("Produce:")
    namesReceiver(namesProducer())
}

@ExperimentalCoroutinesApi
fun CoroutineScope.namesProducer() = produce {
    arrayOf("Eve", "John", "Mary").forEach { send(it) }
}

suspend fun namesReceiver(channel: ReceiveChannel<String>, prefix: String = "-") {
    for (name in channel) {
        logger.info("$prefix $name")
    }
}

@ExperimentalCoroutinesApi
fun exampleFanOut() = runBlocking {
    logger.info("Fan-out:")
    val channel = namesProducer()
    launch { namesReceiver(channel, prefix = "A") }
    launch { namesReceiver(channel, prefix = "B") }
}

fun exampleFanIn() = runBlocking {
    logger.info("Fan-in:")
    val channel = Channel<String>()

    launch { channelProducer(channel, arrayOf("Mary", "Ann", "John")) }
    launch { channelProducer(channel, arrayOf("Chris", "Mike", "Jerry")) }
    launch { limitedReceiver(channel, limit = 6) }

}

@ExperimentalCoroutinesApi
fun exampleChaining() = runBlocking {
    logger.info("Chaining:")
    namesReceiver(uppercaseProducer(namesProducer()))
}

@ExperimentalCoroutinesApi
fun CoroutineScope.uppercaseProducer(channel: ReceiveChannel<String>) = produce {
    for (text in channel) {
        send(text.uppercase())
    }
}