package com.victor.Airbnb.exception;

public class ReserveNotFoundException extends RuntimeException{
    public ReserveNotFoundException(Long id){
        super("Could not found reserve: " + id);
    }
}
