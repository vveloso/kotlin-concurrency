package coroutines.workshop.realworld.webflux

import kotlinx.coroutines.delay
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.temporal.ChronoUnit

@RestController
class NameFlux {

    @GetMapping("/names", produces = [ MediaType.APPLICATION_JSON_VALUE ])
    suspend fun names(): List<String> {
        delay(500)
        return listOf("Elle", "Mike", "John")
    }

    @GetMapping("/names_flux", produces = [ MediaType.APPLICATION_JSON_VALUE ])
    fun namesFlux() =
        Flux.fromArray(arrayOf("Elle", "Mike", "John"))
            .delaySequence(Duration.of(500, ChronoUnit.MILLIS))
            .collectList() // see https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-codecs-jackson
}