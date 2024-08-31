package com.lotto.domain.resultannouncer;

import org.springframework.data.mongodb.repository.MongoRepository;

interface ResponseRepository extends MongoRepository<Response, String> {
    
    boolean existsById(String id);
}
