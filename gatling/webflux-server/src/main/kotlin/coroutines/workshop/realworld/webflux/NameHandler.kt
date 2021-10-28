package coroutines.workshop.realworld.webflux

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class NameHandler {

    @GetMapping("/name", produces = [ MediaType.APPLICATION_JSON_VALUE ])
    fun name() =
        Flux.fromArray(arrayOf("Elle", "Mike", "John"))
            .collectList() // see https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-codecs-jackson

}