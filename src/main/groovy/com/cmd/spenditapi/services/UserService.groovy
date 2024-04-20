package com.cmd.spenditapi.services

import com.cmd.spenditapi.models.User
import com.cmd.spenditapi.repository.UserRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

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


    User getUserById(int id) {
        userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))

    }
}

