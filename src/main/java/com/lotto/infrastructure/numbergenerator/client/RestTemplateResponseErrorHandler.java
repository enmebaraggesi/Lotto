package com.lotto.infrastructure.numbergenerator.client;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {
    
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode.is5xxServerError()) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Error while using http client");
        } else if (statusCode.is4xxClientError()) {
            if (statusCode.value() == NOT_FOUND.value()) {
                throw new ResponseStatusException(NOT_FOUND);
            } else if (statusCode.value() == UNAUTHORIZED.value()) {
                throw new ResponseStatusException(UNAUTHORIZED);
            }
        }
    }
}
