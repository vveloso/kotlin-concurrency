package coroutines.workshop.realworld.reference

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Name {

    @GetMapping("/name")
    fun name() =
        listOf("Mary", "John", "Anne")

}