package com.victor.Airbnb.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(UUID id){
        super("Could not found user: " + id);
    }
}
