package com.lotto.domain.resultannouncer;

import java.util.Optional;

interface ResponseRepository {
    
    boolean existsById(String id);
    
    Optional<Response> findById(String id);
    
    Response save(Response response);
}
