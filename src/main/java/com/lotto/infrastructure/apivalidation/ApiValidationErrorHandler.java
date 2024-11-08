package com.lotto.infrastructure.apivalidation;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ApiValidationErrorHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiValidationErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> errors = getErrorsFromException(exception);
        return new ApiValidationErrorDto(errors, HttpStatus.BAD_REQUEST);
    }
    
    private List<String> getErrorsFromException(final MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                        .getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
    }
}
