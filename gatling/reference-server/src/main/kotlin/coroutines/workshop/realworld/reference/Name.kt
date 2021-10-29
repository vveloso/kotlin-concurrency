package coroutines.workshop.realworld.reference

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Name {

    @GetMapping("/names")
    fun names(): List<String> {
        Thread.sleep(500)
        return listOf("Mary", "John", "Anne")
    }
}