package com.cmd.spenditapi.services

import com.cmd.spenditapi.exceptions.UserNotFoundException
import com.cmd.spenditapi.models.User
import com.cmd.spenditapi.repository.UserRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

import java.util.concurrent.CompletableFuture

@Slf4j
@Service
class UserService {


    private final UserRepository userRepository
    private final BCryptPasswordEncoder passwordEncoder

    @Autowired
    UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository
        this.passwordEncoder = passwordEncoder
    }


    CompletableFuture<User> getUserById(int id) {
        CompletableFuture.supplyAsync(() -> {
            if (id == null || id <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user ID provided!")
            }
            try {
                return userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("User not found"))
            } catch (UserNotFoundException unfe) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, unfe.getMessage())
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
            }
        }).exceptionally({ throwable ->
            if (throwable instanceof ResponseStatusException) {
                ResponseStatusException rse = (ResponseStatusException) throwable
                log.error("Get user error", rse)
                throw rse
            } else {
                log.error("Unexpected error retrieving user", throwable)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve user")
            }
        })
    }

    CompletableFuture<String> createUser(User user) {
        CompletableFuture.supplyAsync(() -> {
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is missing")
            }
            if (!user.getUserName() || !user.getEmail() || !user.getPassword()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide all required fields")
            }
            if (!user.getEmail().matches(/^[A-Za-z0-9+_.-]+@(.+)$/)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format")
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists")
            }
            try {
                String encodedPassword = passwordEncoder.encode(user.getPassword())
                user.setPassword(encodedPassword)
                if (!user.getImage()) {
                    user.setImage("default.png")
                }
                user.setActive(true)
                userRepository.save(user)
                "User created successfully"
            } catch (ResponseStatusException rse) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists")
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
            }
        }).exceptionally({throwable ->
            if(throwable instanceof ResponseStatusException){
                ResponseStatusException rse = (ResponseStatusException) throwable
                log.error("Request processing error", rse)
                throw rse
            }
            else{
                log.error("Unexpected error creating user", throwable)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create user")
            }
        })
    }
    CompletableFuture<String> deleteUserByID(int id){
        CompletableFuture.supplyAsync(() -> {
            if (id == null || id <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user ID provided!")
            }
            try {
                User user = userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("User not found"))
                user.setIsActive(false)
                userRepository.save(user)
                "success"
            }catch(ResponseStatusException rse){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, rse.getMessage())
            }
        }
        ).exceptionally ({ throwable ->
            if (throwable instanceof ResponseStatusException) {
                ResponseStatusException rse = (ResponseStatusException) throwable
                log.error("Request processing error", rse)
                throw rse
            } else {
                log.error("Unexpected error creating user", throwable)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create user")
            }
        })
    }
}

