package nl.codenomads.workshop

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitObjectResponse
import com.github.kittinunf.fuel.serialization.kotlinxDeserializerOf
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.apache.commons.text.StringEscapeUtils
import kotlin.system.measureTimeMillis

@Serializable
data class HelloTranslation(val code: String, val hello: String)

fun main() = runBlocking {

    val duration = measureTimeMillis {

        println(getHelloInLanguage("fr"))

    }

    println("Operation took $duration ms")
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
