package generator.controller

import generator.data.UserModel
import generator.service.UserServiceImpl
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController {
    @Autowired
    lateinit var userService:UserServiceImpl
    private val logger = KotlinLogging.logger {}

    @GetMapping("/v1/users/{seed}")
    fun getUser(@PathVariable seed: String): ResponseEntity<out Any> {
        logger.info { "fun: getUser controller with $seed" }

        val resp = userService.getUser(seed)
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body<List<UserModel>>(resp)
    }
}