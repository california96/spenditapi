package com.cmd.spenditapi.services

import com.cmd.spenditapi.exceptions.UserNotFoundException
import com.cmd.spenditapi.models.User
import com.cmd.spenditapi.repository.UserRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.concurrent.CompletableFuture

@Slf4j
@Service
class UserService {


    private final UserRepository userRepository
    private final BCryptPasswordEncoder passwordEncoder

    @Autowired
    UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository
        this.passwordEncoder = passwordEncoder
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

    CompletableFuture<String> createUser(User user){
        CompletableFuture.supplyAsync(()->{
            try{
                if(userRepository.existsByEmail(user.getEmail())){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already exists!")
                }

                user.setPassword(passwordEncoder.encode(user.getPassword()))
                //todo: for now image will save a default value. Uploading and getting file name will be a separate ticket
                user.setImage("default.png")
                userRepository.save(user)

                return "{\"message\": \"User created successfully\"}"
            } catch(ResponseStatusException rse){
                throw rse
            } catch (Exception e){

            }

        }).exceptionally(ex ->{
            if(ex.getCause() instanceof ResponseStatusException) {
                ResponseStatusException rse = (ResponseStatusException) ex.getCause()
                "{\"message\": \"${rse.getMessage()}\"}"
            } else {
                "{\"message\": \"${ex.getMessage()}\"}"
            }
        })
    }
}

