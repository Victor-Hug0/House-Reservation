package com.victor.Airbnb.advice;

import com.victor.Airbnb.exception.HouseNotFountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HouseNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(HouseNotFountException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String houseNotFoundHandler(HouseNotFountException exception){
        return exception.getMessage();
    }
}
