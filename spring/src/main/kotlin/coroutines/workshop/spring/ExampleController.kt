package coroutines.workshop.spring

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.future.await
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

data class Quote(val q: String, val a: String)

@RestController
class ExampleController(val mapper: ObjectMapper) {

    private val logger = KotlinLogging.logger {}
    private val client = HttpClient.newHttpClient()
    private val allQuotesRequest = quoteRequest()

    @GetMapping("/hello")
    suspend fun hello(): String {
        logger.info { "thinking" }
        delay(10)
        logger.info { "done thinking" }
        return "Hello World"
    }

    @GetMapping("/quotes")
    suspend fun quotes() =
        fetchQuotes(allQuotesRequest)
            .map { "${it.q}\n" }

    private suspend fun fetchQuotes(request: HttpRequest) = flow {
        client.sendAsync(request, BodyHandlers.ofString())
            .await()
            .body()
            .let { mapper.readValue(it, Array<Quote>::class.java) }
            .forEach {
                emit(it)
            }
    }

    private fun quoteRequest() =
        HttpRequest.newBuilder()
            .uri(URI.create("https://zenquotes.io/api/quotes"))
            .GET()
            .build()

}
