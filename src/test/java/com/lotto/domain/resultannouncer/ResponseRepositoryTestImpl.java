package com.lotto.domain.resultannouncer;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class ResponseRepositoryTestImpl implements ResponseRepository {
    
    private final Map<String, Response> inMemoryDatabase = new ConcurrentHashMap<>();
    
    @Override
    public boolean existsById(final String id) {
        return inMemoryDatabase.containsKey(id);
    }
    
    @Override
    public Optional<Response> findById(final String id) {
        return Optional.of(inMemoryDatabase.get(id));
    }
    
    @Override
    public Response save(final Response response) {
        inMemoryDatabase.put(response.id(), response);
        return response;
    }
}
