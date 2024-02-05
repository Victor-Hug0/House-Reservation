package com.victor.Airbnb.advice;

import com.victor.Airbnb.exception.HouseNotFountException;
import com.victor.Airbnb.exception.ReserveNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ReserveNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ReserveNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String reserveNotFoundHandler(ReserveNotFoundException exception){
        return exception.getMessage();
    }
}
