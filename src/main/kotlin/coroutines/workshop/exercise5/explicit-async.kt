package coroutines.workshop.exercise5

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitObjectResponse
import com.github.kittinunf.fuel.serialization.kotlinxDeserializerOf
import coroutines.workshop.common.logger
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.apache.commons.text.StringEscapeUtils
import kotlin.system.measureTimeMillis

/*
  Explicit concurrency.

  1. Measure the time it takes to get one translation of hello in one language.

  2. Get the translation for at least four languages, print them while measuring the time again.

  3. Issue the requests concurrently (hint: async()). Still measuring the time.

  4. Did the third version run faster?
 */

@Serializable
data class HelloTranslation(val code: String, val hello: String)

fun main() = runBlocking {

    val duration = measureTimeMillis {

        val french = async { getHelloInLanguage("fr") }
        val spanish = async { getHelloInLanguage("sp") }
        val portuguese = async { getHelloInLanguage("pt") }
        val english = async { getHelloInLanguage("en") }

        logger.info("${french.await()} ${spanish.await()} ${portuguese.await()} ${english.await()} ")

    }

    logger.info("Operation took $duration ms")
}

// very simplified function for retrieving the language code - lacks error handling
// see https://fuel.gitbook.io/documentation/
suspend fun getHelloInLanguage(languageCode: String): String =
    StringEscapeUtils.unescapeHtml4(
        Fuel.get("https://fourtonfish.com/hellosalut", listOf("lang" to languageCode)) // see https://www.fourtonfish.com/hellosalut/hello/
            .awaitObjectResponse(kotlinxDeserializerOf(HelloTranslation.serializer()))
            .third
            .hello
    )
