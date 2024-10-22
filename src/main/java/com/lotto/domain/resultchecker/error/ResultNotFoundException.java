package com.lotto.domain.resultchecker.error;

public class ResultNotFoundException extends RuntimeException {
    
    public ResultNotFoundException(final String message) {
        super(message);
    }
}
