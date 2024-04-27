package com.cmd.spenditapi.exceptions

class UserNotFoundException extends RuntimeException{
    UserNotFoundException(String message){
        super(message)
    }
}
