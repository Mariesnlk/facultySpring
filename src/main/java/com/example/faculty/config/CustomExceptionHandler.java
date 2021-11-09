package com.example.faculty.config;

import com.example.faculty.exception.NotEnoughRightsException;
import com.example.faculty.util.mvc_responses.model.MvcResponseObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MvcResponseObject handleException(Exception e) {
        return new MvcResponseObject(400, "Error - " + e.getMessage());
    }

    @ExceptionHandler(NotEnoughRightsException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ResponseBody
    public MvcResponseObject handleNotEnoughRightsException(NotEnoughRightsException e) {
        return new MvcResponseObject(403, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public MvcResponseObject handleBadCredentialsException(BadCredentialsException e) {
        return new MvcResponseObject(401, e.getMessage());
    }

}
