package com.victor.Airbnb.exception;

public class HouseNotFountException extends RuntimeException{

    public HouseNotFountException(Long id){
        super("Could not found house: " + id);
    }
}
