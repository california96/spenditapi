package com.cmd.spenditapi.services

import com.cmd.spenditapi.exceptions.UserNotFoundException
import com.cmd.spenditapi.models.User
import com.cmd.spenditapi.repository.UserRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

import java.util.concurrent.CompletableFuture

@Slf4j
@Service
class UserService {

    static final String COL_NAME = "users"

    @Autowired
    UserRepository userRepository

    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository = userRepository
    }


    CompletableFuture<User> getUserById(int id) {
        CompletableFuture.supplyAsync(() -> {
            try {
                return userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("User not found"))
            } catch (UserNotFoundException unfe) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, unfe.getMessage())
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
            }
        })
    }
}

