package com.cmd.spenditapi.controller
import com.cmd.spenditapi.models.User
import com.cmd.spenditapi.models.Views
import com.cmd.spenditapi.repository.UserRepository
import com.cmd.spenditapi.services.UserService
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

@RestController
class UserController {
    @Autowired
    private UserService userService

    @Autowired
    UserController(UserService userService){
        this.userService = userService
    }

    @GetMapping("/user/{id}")
    @JsonView(Views.Public.class)
    CompletableFuture<User> getUser(@PathVariable("id") int userID) {
        userService.getUserById(userID)
    }

    @PostMapping("/user/create")
    @JsonView(Views.Internal.class)
    CompletableFuture<String> createUser(User user){
        userService.createUser(user)
    }

}
