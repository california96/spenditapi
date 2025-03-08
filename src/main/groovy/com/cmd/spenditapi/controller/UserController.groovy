package com.cmd.spenditapi.controller
import com.cmd.spenditapi.models.User
import com.cmd.spenditapi.models.Views
import com.cmd.spenditapi.services.UserService
import com.fasterxml.jackson.annotation.JsonView
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/user")
@Tag(name = "User Module", description = "Operations related to user management")
class UserController {
    @Autowired
    private UserService userService

    @Autowired
    UserController(UserService userService){
        this.userService = userService
    }

    @GetMapping("/{id}")
    @JsonView(Views.Public.class)
    CompletableFuture<User> getUser(@PathVariable("id") int userID) {
        userService.getUserById(userID)
    }

    @PostMapping("/create")
    @JsonView(Views.Internal.class)
    CompletableFuture<String> createUser(@RequestBody @JsonView(Views.Internal.class) User user){
        userService.createUser(user)
    }

    @DeleteMapping("/delete/{id}")
    @JsonView(Views.Internal.class)
    CompletableFuture<String> deleteUser(@PathVariable("id") int userID){
        userService.deleteUserByID(userID)
    }

}
