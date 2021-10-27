package coroutines.workshop.exercise8

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitObjectResponse
import com.github.kittinunf.fuel.serialization.kotlinxDeserializerOf
import coroutines.workshop.common.logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.apache.commons.text.StringEscapeUtils

/*

  Challenge:

  Build a Flow that provides ten items retrieved from an API using the HTTP client from before.

 */

@Serializable
data class RandomQuote(
    @SerialName("q") val quote: String,
    @SerialName("a") val author: String
)

fun main() = runBlocking {
    flowingQuotes().collect {
        logger.info(it)
        delay(6_000) // free API limit: 5 requests per 30 second period
    }
}

fun flowingQuotes() = flow {
    repeat(10) {
        emit(getRandomQuote())
    }
}

// very simplified function for calling an API - lacks error handling
suspend fun getRandomQuote(): String =
    StringEscapeUtils.unescapeHtml4(
        Fuel.get("https://zenquotes.io/api/random") // see https://zenquotes.io/
            .awaitObjectResponse(kotlinxDeserializerOf(ListSerializer(RandomQuote.serializer()), Json { ignoreUnknownKeys = true }))
            .let { it.third.first().let { "${it.quote} - ${it.author}" } }
    )
