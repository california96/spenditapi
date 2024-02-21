package com.cmd.spenditapi.controller
import com.cmd.spenditapi.models.User
import com.cmd.spenditapi.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

@RestController
class UserController {
    @Autowired
    UserService userService

    @GetMapping("/user/{id}")
    CompletableFuture<ResponseEntity<User>> getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id)
                .thenApply(user -> ResponseEntity.ok().body(user)) // Convert User to ResponseEntity with HTTP 200 OK status
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); // Handle exception with HTTP 404 NOT FOUND status
    }
}
