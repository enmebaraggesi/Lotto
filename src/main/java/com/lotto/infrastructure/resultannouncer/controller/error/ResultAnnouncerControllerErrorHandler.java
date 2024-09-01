package com.lotto.infrastructure.resultannouncer.controller.error;

import com.lotto.domain.resultchecker.error.ResultNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class ResultAnnouncerControllerErrorHandler {
    
    @ExceptionHandler(ResultNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResultAnnouncerErrorResponse handleResultNotFoundException(ResultNotFoundException exception) {
        String message = exception.getMessage();
        log.error(message);
        return new ResultAnnouncerErrorResponse(message, HttpStatus.NOT_FOUND);
    }
}
